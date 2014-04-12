package in.regalauction.domain.model.attachment;

import org.apache.commons.lang.Validate;

/**
 * 
 * @author Diptangshu Chakrabarty
 * @since 1
 *
 */
public class Image extends Attachment {
	
	private int height;
	private int width;
	
	public static class Builder {
		// Required parameter
		private String code;
		private String filename;
		
		// optional paramaters
		private int height = 0;
		private int width = 0;
		
		public Builder(final String code, final String filepath) {
			this.code = code;
			this.filename = filepath;
		}
		
		public Builder height(final int height) {
			Validate.isTrue(height >= 0);
			this.height = height;
			return this;
		}
		
		public Builder width(final int width) {
			Validate.isTrue(width >= 0);
			this.width = width;
			return this;
		}
		
		public Image build() {
			return new Image(this);
		}
	}
	
	private Image(Builder builder) {
		super(builder.code, builder.filename);
		
		this.height = builder.height;
		this.width = builder.width;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	Image() {
		// Needed by Hibernate
	}
}
