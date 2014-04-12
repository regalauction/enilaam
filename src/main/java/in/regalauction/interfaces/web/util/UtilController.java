package in.regalauction.interfaces.web.util;

import in.regalauction.application.AddressFinderService;
import in.regalauction.domain.model.attachment.Attachment;
import in.regalauction.domain.model.attachment.AttachmentRepository;
import in.regalauction.domain.model.attachment.AttachmentType;
import in.regalauction.infrastructure.io.AttachmentManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UtilController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilController.class);
	
	@Autowired
	private AddressFinderService addressFinderService;
	
	@Autowired
	private AttachmentManager attachmentManager;
	
	@Autowired
	private AttachmentRepository attachmentRepository;

	@RequestMapping("/util/cities")
	public @ResponseBody List<String> getCities(@RequestParam("state") String state) {
		LOGGER.trace("getCities({})", state);
		return addressFinderService.getCities(state);
	}
	
	@RequestMapping("/attachments/images/{filename:.*}")
	public @ResponseBody byte[] getImage(@PathVariable("filename") String filename) throws IOException {
		LOGGER.trace("Fetching image '{}'", filename);
		return attachmentManager.getImage(filename);
	}
	
	@RequestMapping("/attachments/documents/{filename:.*}")
	public void getDocument(@PathVariable("filename") String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOGGER.trace("Fetching document '{}'", filename);
		
		FileInputStream document = attachmentManager.getDocument(filename);
		ServletContext servletContext = request.getSession().getServletContext();
		String mimeType = servletContext.getMimeType(filename);
		
		response.setContentType(mimeType);
		String contentDisposition = new StringBuilder("attachment; filename=\"").append(filename).append("\"").toString();
		response.setHeader("Content-Disposition", contentDisposition);
		
		FileCopyUtils.copy(document, response.getOutputStream());
		document.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping("/attachments/upload/{attachmentType}")
	@Transactional
	public @ResponseBody String uploadAttachment(@RequestParam MultipartFile file, @PathVariable("attachmentType") AttachmentType attachmentType) throws IOException {
		LOGGER.trace("Uploading attachment...");
		LOGGER.debug("Attachment Type: {}", attachmentType.toString());
		
		Attachment attachment = attachmentType == AttachmentType.DOCUMENT? 
				attachmentManager.uploadDocument(file) : attachmentManager.uploadImage(file);
				
		attachmentRepository.store(attachment);
		
		return attachment.getCode();
	}
}
