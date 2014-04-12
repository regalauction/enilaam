package in.regalauction.domain.model.attachment;

import in.regalauction.domain.shared.Entity;

import org.apache.commons.lang.Validate;


public abstract class Attachment implements Entity<Attachment> {

	private String code;
	private String name;
	
	public Attachment(final String code, final String filepath) {
		Validate.notEmpty(code);
		Validate.notNull(filepath);
		this.code = code;
		this.name = filepath;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}

	@Override
	public boolean sameIdentityAs(final Attachment other) {
		return other!=null && this.code.equals(other.code);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		final Attachment other = (Attachment) obj;
		
		return sameIdentityAs(other);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	Long id;
	
	Attachment() {
		// Needed by Hibernate
	}
}
