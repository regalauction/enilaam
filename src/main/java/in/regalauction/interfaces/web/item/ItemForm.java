package in.regalauction.interfaces.web.item;

import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.item.Item;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;


public class ItemForm {

	@NotBlank private String code;
	@NotBlank private String name;
//	private String currThumbnailPath;
	
	private Set<Image> currImages;
	private String[] addFiles;
	private String[] deleteFiles;
	
	private boolean existing;

	public ItemForm() {
		currImages = new HashSet<Image>();
		existing = false;
	}
	
	public ItemForm(final Item item) {
		this.code = item.getCode();
		this.name = item.getName();
		currImages = item.getImages();
		existing = true;
//		if (item.getThumbnail() != null)
//			this.currThumbnailPath = item.getThumbnail().getCode();
	}
	
//	public String getCurrThumbnailPath() {
//		return currThumbnailPath;
//	}
	
	public boolean isExisting() {
		return existing;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getAddFiles() {
		return addFiles;
	}

	public void setAddFiles(String[] addFiles) {
		this.addFiles = addFiles;
	}

	public String[] getDeleteFiles() {
		return deleteFiles;
	}

	public void setDeleteFiles(String[] deleteFiles) {
		this.deleteFiles = deleteFiles;
	}

	public Set<Image> getCurrImages() {
		return currImages;
	}

}
