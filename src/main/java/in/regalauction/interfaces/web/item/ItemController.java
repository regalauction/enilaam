package in.regalauction.interfaces.web.item;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.regalauction.domain.model.attachment.AttachmentRepository;
import in.regalauction.domain.model.attachment.AttachmentType;
import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.item.ItemRepository;
import in.regalauction.infrastructure.io.AttachmentManager;


@Controller
@RequestMapping("/item")
@Transactional(readOnly = true)
public class ItemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private AttachmentManager attachmentManager;
	
	@Autowired
	private AttachmentRepository attachmentRepository;
	
	@ModelAttribute("attachmentType")
	public AttachmentType populateAttachmentType() {
		return AttachmentType.IMAGE;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ItemForm getNewForm() {
		return new ItemForm();
	}

	@RequestMapping(value = "/{itemCode}", method = RequestMethod.GET)
	public ItemForm getEditForm(@PathVariable("itemCode") String itemCode) {
		Item item = itemRepository.findByCode(itemCode);
		Validate.notNull(item);
		return new ItemForm(item);
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = IOException.class)
	public String add(@Valid ItemForm itemForm, BindingResult result) throws IOException {
		
		if (result.hasErrors()) return "item/new";
		
		final String code = itemForm.getCode();
		final String name = itemForm.getName();
		
		final Item item = new Item(code, name);
		BeanUtils.copyProperties(itemForm, item);
		
		manageImages(itemForm, item);
		
		itemRepository.store(item);
		
		String returnView = new StringBuilder("item/").append(code).toString();
		return new StringBuilder("redirect:/").append(returnView).toString();
	}

	
	@RequestMapping(value = "/{itemCode}", method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = IOException.class)
	public String update(@PathVariable("itemCode") String itemCode,  @Valid ItemForm itemForm, BindingResult result) throws IOException {
		
		LOGGER.trace("updating Item: {}", itemCode);
		
		LOGGER.debug("Has binding errors? {}: {}", result.hasErrors(), result);
		
		String returnView = new StringBuilder("item/").append(itemCode).toString();
		
		if (result.hasErrors())
			return returnView;
		
		final Item item = itemRepository.findByCode(itemCode);
		BeanUtils.copyProperties(itemForm, item);
		
		manageImages(itemForm, item);
		
		itemRepository.store(item);
		
		return new StringBuilder("redirect:/").append(returnView).toString();
	}
	
	private void manageImages(final ItemForm itemForm, final Item item) {
		
		List<String> addFiles = Arrays.asList(itemForm.getAddFiles());
		List<String> deleteFiles = Arrays.asList(itemForm.getDeleteFiles());
		
		LOGGER.debug("{} images uploaded: {}", addFiles.size(), addFiles);
		for (String fileCode : addFiles) {
			if (!fileCode.isEmpty() && !deleteFiles.contains(fileCode)) {
				Image image = (Image) attachmentRepository.findByCode(fileCode);
				item.addImage(image);
			}
		}
		
		LOGGER.debug("Removing {} images: {}", deleteFiles.size(), deleteFiles);
		for (String fileCode : deleteFiles) {
			if (!fileCode.isEmpty()) {
				item.removeImage(fileCode);
			}
		}
	}
	
}
 