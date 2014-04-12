package in.regalauction.domain.model.attachment;

public interface AttachmentRepository {
	
	Attachment findByCode(String code);

	void store(Attachment attachment);
}
