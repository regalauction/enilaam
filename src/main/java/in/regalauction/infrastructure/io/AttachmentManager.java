package in.regalauction.infrastructure.io;

import in.regalauction.domain.model.attachment.Document;
import in.regalauction.domain.model.attachment.Image;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface AttachmentManager {

	public Image uploadImage(MultipartFile file) throws IOException;
	
	public Document uploadDocument(MultipartFile file) throws IOException;

	public byte[] getImage(String filename) throws IOException;
	
	public FileInputStream getDocument(String filename) throws IOException;
}
