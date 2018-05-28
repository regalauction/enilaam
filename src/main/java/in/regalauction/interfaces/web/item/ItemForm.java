package in.regalauction.interfaces.web.item;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.item.Item;


public class ItemForm {

	@NotBlank private String code;
	@NotBlank private String name;
	
	private String unitOfMeasure;
	private String field1;
	private String field2;
	private String field3;
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
		this.unitOfMeasure = item.getUnitOfMeasure();
		this.field1 = item.getField1();
		this.field2 = item.getField2();
		this.field3 = item.getField3();
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

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public String getField3() {
		return field3;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}
	
	
}
