package in.regalauction.interfaces.web.spotdeal;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.spotdeal.SpotDealItem;


public class SpotDealItemForm {

	@NotBlank private String code;
	@NotBlank private String name;
	
	private String unitOfMeasure;
	
	@NotNull
	@Min(1)
	@Digits(integer = 8, fraction = 2)
	private BigDecimal quantity;
	
	private String deliveryPeriod;
	
	private String address;
	private String city;
	private String state;
	@Pattern(regexp = "\\s*|\\d{6}")
	private String pincode;
	
	private String spec;
	
	@NotNull
	@Min(0)
	@Digits(integer = 8, fraction = 2) 
	private BigDecimal basePrice;
	
	@NotBlank
	private String sellerName;
	
	@NotBlank
	@Email
	private String sellerEmail;
	
	private boolean enabled;
		
//	private String currThumbnailPath;
	
	private Set<Image> currImages;
	private String[] addFiles;
	private String[] deleteFiles;
	
	private boolean existing;

	public SpotDealItemForm() {
		currImages = new HashSet<Image>();
		existing = false;
		enabled = true;
	}
	
	public SpotDealItemForm(final SpotDealItem spotDealItem) throws IllegalAccessException, InvocationTargetException {
		this.code = spotDealItem.getCode();
		this.name = spotDealItem.getName();
		
		if (spotDealItem.getAddress() != null) BeanUtils.copyProperties(this, spotDealItem.getAddress());
		
		this.unitOfMeasure = spotDealItem.getUnitOfMeasure();
		this.quantity = new BigDecimal(spotDealItem.getQuantity());
		this.basePrice = spotDealItem.getBasePrice() != null ? spotDealItem.getBasePrice().getAmount() : new BigDecimal(0);
		this.deliveryPeriod = spotDealItem.getDeliveryPeriod();
		this.enabled = spotDealItem.isEnabled();
		this.spec = spotDealItem.getSpec();
		this.sellerName = spotDealItem.getSellerName();
		this.sellerEmail = spotDealItem.getSellerEmail();
		
		currImages = spotDealItem.getImages();
		
		
		existing = true;
//		if (item.getThumbnail() != null)
//			this.currThumbnailPath = item.getThumbnail().getCode();
	}
	
//	public String getCurrThumbnailPath() {
//		return currThumbnailPath;
//	}
	
	
	public String getSpec() {
		return spec;
	}
	
	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	
	public boolean isExisting() {
		return existing;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}


	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getDeliveryPeriod() {
		return deliveryPeriod;
	}

	public void setDeliveryPeriod(String deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}

	public void setCurrImages(Set<Image> currImages) {
		this.currImages = currImages;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
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
