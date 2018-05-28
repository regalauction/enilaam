package in.regalauction.infrastructure.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import in.regalauction.domain.model.attachment.Document;
import in.regalauction.domain.model.attachment.Image;



public class AttachmentManagerImpl implements AttachmentManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentManagerImpl.class);
	
	private String imageDir;
	private String documentDir;
	
	public AttachmentManagerImpl(final String imageDir, final String documentDir) {
		
		LOGGER.trace("Setting file directories...");
		LOGGER.trace("Image directory: {}", imageDir);
		LOGGER.trace("Document directory: {}", documentDir);
		
		Validate.notEmpty(imageDir);
		Validate.notEmpty(documentDir);
		
		this.imageDir = imageDir;
		this.documentDir = documentDir;
	}

	@Override
	public Image uploadImage(final MultipartFile file) throws IOException {
		return new Image.Builder(upload(file, imageDir), file.getOriginalFilename()).build();
	}
	
	@Override
	public Document uploadDocument(final MultipartFile file) throws IOException {
		return new Document(upload(file, documentDir), file.getOriginalFilename());
	}
	
	private static String generateCode(final String filename) {
		LOGGER.trace("Generating code for filename: {}", filename);
		String uuid = UUID.randomUUID().toString();
		String ext = filename.substring(filename.lastIndexOf('.'));
		String code = uuid.substring(0, uuid.indexOf("-")).concat(ext);
		LOGGER.debug("Generated code {} for filename {}", code, filename);
		return code;
	}

	@Override
	public byte[] getImage(final String filename) throws IOException {
		LOGGER.trace("Reading image {}", filename);
		return FileUtils.readFileToByteArray(new File(new StringBuilder(imageDir).append(filename).toString()));
	}
	
	@Override
	public FileInputStream getDocument(final String filename) throws IOException {
		LOGGER.trace("Fetching document {}", filename);
		return FileUtils.openInputStream(new File(new StringBuilder(documentDir).append(filename).toString()));
	}
	
	private static String upload(final MultipartFile file, final String path) throws IOException {
		Validate.isTrue(file.getSize() > 0);
		
		String filename = file.getOriginalFilename();
		String code = generateCode(filename);
		
		LOGGER.debug("Saving {} to {}", code, path);
		
		FileOutputStream outputStream = FileUtils.openOutputStream(new File(new StringBuilder(path).append(code).toString()));
		outputStream.write(file.getBytes());
		outputStream.close();
		
		LOGGER.debug("{} uploaded successfully", filename);
		
		return code;
	}
	
	
}
