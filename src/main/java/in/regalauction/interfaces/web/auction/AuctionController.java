package in.regalauction.interfaces.web.auction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.regalauction.application.UserService;
import in.regalauction.domain.model.attachment.AttachmentRepository;
import in.regalauction.domain.model.attachment.AttachmentType;
import in.regalauction.domain.model.attachment.Document;
import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.AuctionCode;
import in.regalauction.domain.model.auction.AuctionRepository;
import in.regalauction.domain.model.auction.DutchAuction;
import in.regalauction.domain.model.auction.EnglishAuction;
import in.regalauction.domain.model.auction.OpenAuction;
import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.item.ItemRepository;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.infrastructure.io.AttachmentManager;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;


@Controller
@RequestMapping("/auction")
@Transactional(readOnly = true)
public class AuctionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionController.class);

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttachmentManager attachmentManager;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@ModelAttribute("auctionTypes")
	public Collection<AuctionType> populateAuctionTypes() {
		ArrayList<AuctionType> auctionTypes = new ArrayList<AuctionType>();
		auctionTypes.add(AuctionType.ENGLISHOPEN);
		auctionTypes.add(AuctionType.DUTCHOPEN);
		return auctionTypes;
	}
	
	@ModelAttribute("attachmentType")
	public AttachmentType populateAttachmentType() {
		return AttachmentType.DOCUMENT;
	}
	
	@ModelAttribute("items")
	public Collection<Item> populateItems() {
		return itemRepository.findAll();
	}
	
	@ModelAttribute("bidders")
	public Collection<User> populateBidders() {
		return userService.findBidders();
	}
	
	@ModelAttribute("observers")
	public Collection<User> populateObservers() {
		return userService.findObservers();
	}
	
	@RequestMapping(value ="/new", method = RequestMethod.GET)
	public AuctionForm getNewForm() {
		return new AuctionForm();
	}
	
	@RequestMapping(value = "/{auctionCode}", method = RequestMethod.GET)
	public AuctionForm getEditForm(@PathVariable("auctionCode") String auctionCode, ModelMap map) throws Exception {
		Auction auction = auctionRepository.findByAuctionCode(new AuctionCode(auctionCode));
		Validate.notNull(auction);
		
		// Compute number of bidders and observers
		map.addAttribute("numBidders", CollectionUtils.intersection(auction.getUsers(), userService.findBidders()).size());
		map.addAttribute("numObservers", CollectionUtils.intersection(auction.getUsers(), userService.findObservers()).size());
		
		return new AuctionForm(new AuctionViewAdapter(auction));
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String add(@Valid AuctionForm auctionForm, BindingResult result) throws Exception {
		LOGGER.trace("add() called");
		
		DateTime startDate = auctionForm.getStartDate();
		DateTime endDate = auctionForm.getEndDate();
		if (endDate.isBefore(startDate))
			result.rejectValue("endDate", "Invalid.auctionForm.endDate");
		
		if (result.hasErrors())	return "auction/new";
		
		Item item = itemRepository.findByCode(auctionForm.getItem());
		OpenAuction auction;
		
		AuctionCode nextAuctionCode = auctionRepository.nextAuctionCode();
		
		auction = auctionForm.getAuctionType() == AuctionType.ENGLISHOPEN ? 
				new EnglishAuction(nextAuctionCode, 
					auctionForm.getName(), 
					item, 
					new Money(auctionForm.getBasePrice()), 
					new Money(auctionForm.getReservePrice()), 
					new Money(auctionForm.getDeltaPrice()), 
					startDate, 
					endDate, 
					auctionForm.getTimeExtension()) : 
				new DutchAuction(nextAuctionCode, 
					auctionForm.getName(), 
					item, 
					new Money(auctionForm.getBasePrice()), 
					new Money(auctionForm.getReservePrice()), 
					new Money(auctionForm.getDeltaPrice()), 
					startDate, 
					endDate, 
					auctionForm.getTimeExtension());
		
		setBidders(auctionForm, auction);
		auction.setQuantity(auctionForm.getQuantity());
		
		manageDocuments(auctionForm, auction);
		
		auctionRepository.store(auction);
		
		String returnView = new StringBuilder("auction/").append(nextAuctionCode).toString();
		return new StringBuilder("redirect:/").append(returnView).toString();
	}

	

	@RequestMapping(value = "/{auctionCode}", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String update(@PathVariable("auctionCode") String auctionCode, @Valid AuctionForm auctionForm, BindingResult result) throws Exception {
		LOGGER.trace("update() called");
		
		String returnView = new StringBuilder("auction/").append(auctionCode).toString();
		
		if (auctionForm.getEndDate().isBefore(auctionForm.getStartDate()))
			result.rejectValue("endDate", "Invalid.auctionForm.endDate");
		
		if (result.hasErrors()) return returnView;
		
		Item item = itemRepository.findByCode(auctionForm.getItem());
		OpenAuction auction;
		
		auction = (OpenAuction) auctionRepository.findByAuctionCode(new AuctionCode(auctionCode));
		
		auction.setItem(item);
		auction.setQuantity(auctionForm.getQuantity());
		auction.setName(auctionForm.getName());
		auction.setStartDate(auctionForm.getStartDate());
		auction.setEndDate(auctionForm.getEndDate());
		auction.setBasePrice(new Money(auctionForm.getBasePrice()));
		auction.setReservePrice(new Money(auctionForm.getReservePrice()));
		auction.setDeltaPrice(new Money(auctionForm.getDeltaPrice()));
		auction.setTimeExtension(auctionForm.getTimeExtension());
		
		setBidders(auctionForm, auction);
		
		manageDocuments(auctionForm, auction);
		
		auctionRepository.store(auction);
		
		return new StringBuilder("redirect:/").append(returnView).toString();
		
	}

	private void manageDocuments(AuctionForm auctionForm, OpenAuction auction) {
		List<String> addFiles = Arrays.asList(auctionForm.getAddFiles());
		List<String> deleteFiles = Arrays.asList(auctionForm.getDeleteFiles());
		
		LOGGER.debug("{} documents uploaded: {}", addFiles.size(), addFiles);
		for (String fileCode : addFiles) {
			if (!fileCode.isEmpty() && !deleteFiles.contains(fileCode)) {
				Document document = (Document) attachmentRepository.findByCode(fileCode);
				auction.addDocument(document);
			}
		}
		
		LOGGER.debug("Removing {} documents: {}", deleteFiles.size(), deleteFiles);
		for (String fileCode : deleteFiles) {
			if (!fileCode.isEmpty()) {
				auction.removeDocument(fileCode);
			}
		}
	}
	
	private void setBidders(AuctionForm auctionForm, OpenAuction auction) {
		if (!ArrayUtils.isEmpty(auctionForm.getBidders())) {
			
			Set<User> bidders = new HashSet<User>();
			for (String username : auctionForm.getBidders()) {
				User bidder = userService.findByUsername(username);
				bidders.add(bidder);
			}
			
			auction.setUsers(bidders);
		}
	}
	
}
