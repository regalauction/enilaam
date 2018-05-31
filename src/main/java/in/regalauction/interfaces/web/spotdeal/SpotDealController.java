package in.regalauction.interfaces.web.spotdeal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.regalauction.application.AddressFinderService;
import in.regalauction.application.MailingService;
import in.regalauction.application.UserService;
import in.regalauction.domain.model.attachment.AttachmentRepository;
import in.regalauction.domain.model.attachment.AttachmentType;
import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.spotdeal.SpotDealItem;
import in.regalauction.domain.model.spotdeal.SpotDealItemRepository;
import in.regalauction.domain.model.spotdeal.SpotDealOrder;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserRepository;

@Controller
@RequestMapping("/spotdeals")
@Transactional()
public class SpotDealController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpotDealController.class);

	@Autowired
	private SpotDealItemRepository spotDealRepository;
	
	@Autowired
	private AddressFinderService addressFinderService;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailingService mailingService;

	@ModelAttribute
	public void populateModel(Model model) {		
		List<String> states = addressFinderService.getStates();
		model.addAttribute("states", states);
		if (!states.isEmpty()) {
			model.addAttribute("cities", addressFinderService.getCities(states.get(0)));
		}
	}

	@ModelAttribute("attachmentType")
	public AttachmentType populateAttachmentType() {
		return AttachmentType.IMAGE;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public SpotDealItemForm getNewForm() {
		return new SpotDealItemForm();
	}

	@RequestMapping(value = "/{spotDealItemCode}", method = RequestMethod.GET)
	public SpotDealItemForm getEditForm(@PathVariable("spotDealItemCode") String spotDealItemCode, Model model) throws IllegalAccessException, InvocationTargetException {
		SpotDealItem spotDealItem = spotDealRepository.findByCode(spotDealItemCode);
		Validate.notNull(spotDealItem);
		
		if (!spotDealItem.isStateEmpty())
			model.addAttribute("cities", addressFinderService.getCities(spotDealItem.getAddress().getState()));
		
		return new SpotDealItemForm(spotDealItem);
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = IOException.class)
	public String add(@Valid SpotDealItemForm spotDealItemForm, BindingResult result) throws IOException {

		if (result.hasErrors())return "spotdeals/new";

		final String code = spotDealItemForm.getCode();
		final String name = spotDealItemForm.getName();

		final SpotDealItem spotDealItem = new SpotDealItem(code, name);
		
		populate(spotDealItemForm, spotDealItem);

		manageImages(spotDealItemForm, spotDealItem);
		
		spotDealRepository.store(spotDealItem);

		String returnView = new StringBuilder("spotdeals/").append(code).toString();
		return new StringBuilder("redirect:/").append(returnView).toString();
	}

	private void populate(SpotDealItemForm spotDealItemForm, final SpotDealItem spotDealItem) {
		BeanUtils.copyProperties(spotDealItemForm, spotDealItem, new String[] { "images", "basePrice", "address", "quantity" });
		spotDealItem.setAddress(spotDealItemForm.getAddress(), spotDealItemForm.getCity(), spotDealItemForm.getState(), spotDealItemForm.getPincode());
		spotDealItem.setBasePrice(new Money(spotDealItemForm.getBasePrice()));
		spotDealItem.setQuantity(spotDealItemForm.getQuantity().longValue());
	}

	@RequestMapping(value = "/{spotDealItemCode}", method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = IOException.class)
	public String update(@PathVariable("spotDealItemCode") String spotDealItemCode,
			@Valid SpotDealItemForm spotDealItemForm, BindingResult result) throws IOException {

		LOGGER.trace("updating Item: {}", spotDealItemCode);

		LOGGER.debug("Has binding errors? {}: {}", result.hasErrors(), result);

		String returnView = new StringBuilder("spotdeals/").append(spotDealItemCode).toString();

		if (result.hasErrors()) return returnView;

		final SpotDealItem spotDealItem = spotDealRepository.findByCode(spotDealItemCode);
		
		populate(spotDealItemForm, spotDealItem);

		manageImages(spotDealItemForm, spotDealItem);
		
		spotDealRepository.store(spotDealItem);

		return new StringBuilder("redirect:/").append(returnView).toString();
	}
	
	@RequestMapping(value = "/purchase/{spotDealItemCode}", method = RequestMethod.GET)
	public SpotDealPurchaseForm getPurchaseForm(@PathVariable("spotDealItemCode") String spotDealItemCode, ModelMap map) throws IllegalAccessException, InvocationTargetException {
		
		SpotDealItem spotDealItem = spotDealRepository.findByCode(spotDealItemCode);
				
		return new SpotDealPurchaseForm(spotDealItem);
	}
	
	@RequestMapping(value = "/purchase/{spotDealItemCode}", method = RequestMethod.POST)
	public String purchase(@PathVariable("spotDealItemCode") String spotDealItemCode,
			@Valid SpotDealPurchaseForm spotDealPurchaseForm, BindingResult result) {
		
		String returnView = new StringBuilder("spotdeals/purchase/").append(spotDealItemCode).toString();
		
		if (result.hasErrors()) return returnView;
		
		LOGGER.trace("Save the purchase");
		
		SpotDealItem spotDealItem = spotDealRepository.findByCode(spotDealItemCode);
		String username = userService.getCurrentUsername();
		User bidder = userRepository.findByUsername(username);
		DateTime orderTime = new DateTime();
		
		Long quantity = spotDealPurchaseForm.getOrderQuantity().longValue();
		
		if (quantity > spotDealItem.getQuantity()) {
			result.rejectValue("orderQuantity", "Invalid.spotDealPurchaseForm.orderQuantity");
			return returnView;
		}
				

		spotDealItem.placeOrder(new SpotDealOrder(quantity, orderTime, bidder));
		
		mailingService.sendSpotDealOrderMail(spotDealItem, bidder, quantity);
				
		
		return "redirect:/";
	}

	private void manageImages(final SpotDealItemForm spotDealItemForm, final SpotDealItem spotDealItem) {

		List<String> addFiles = Arrays.asList(spotDealItemForm.getAddFiles());
		List<String> deleteFiles = Arrays.asList(spotDealItemForm.getDeleteFiles());

		LOGGER.debug("{} images uploaded: {}", addFiles.size(), addFiles);
		for (String fileCode : addFiles) {
			if (!fileCode.isEmpty() && !deleteFiles.contains(fileCode)) {
				Image image = (Image) attachmentRepository.findByCode(fileCode);
				spotDealItem.addImage(image);
			}
		}

		LOGGER.debug("Removing {} images: {}", deleteFiles.size(), deleteFiles);
		for (String fileCode : deleteFiles) {
			if (!fileCode.isEmpty()) {
				spotDealItem.removeImage(fileCode);
			}
		}
	}
	
}
