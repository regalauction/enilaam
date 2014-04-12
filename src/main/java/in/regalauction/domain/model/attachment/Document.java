package in.regalauction.domain.model.attachment;

/**
 * 
 * @author Diptangshu Chakrabarty
 * @since 1
 *
 */
public class Document extends Attachment {

	public Document(final String code, final String filepath) {
		super(code, filepath);
	}

	Document() {
		// Needed by Hibernate
	}
}
