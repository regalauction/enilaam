package in.regalauction.domain.model.spotdeal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.types.Address;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.shared.Entity;

public class SpotDealItem implements Entity<SpotDealItem> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpotDealItem.class);

	private String code;
	private String name;
	
	private Address address;
	private String unitOfMeasure;
	private Long quantity;
	private Money basePrice;
	private String deliveryPeriod;
	private String spec;
	private String sellerName;
	private String sellerEmail;
	
	private boolean enabled;

	private Image thumbnail;
	private Set<Image> images = Collections.emptySet();
	
	protected List<SpotDealOrder> orders = new ArrayList<SpotDealOrder>();

	public SpotDealItem(final String code, final String name) {
		Validate.notEmpty(code);
		Validate.notNull(name);
		this.code = code;
		this.name = name;
	}
	
	public void placeOrder(final SpotDealOrder order) {
		Validate.notNull(order);
		Validate.isTrue(quantity > 0);
		Validate.isTrue(quantity >= order.getQuantity());
		
		orders.add(order);
		
		quantity -= order.getQuantity();
		
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


	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public boolean isStateEmpty() {
		return address == null || StringUtils.isEmpty(address.getState());
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(final String address, final String city, final String state, final String pincode) {

		this.address = new Address(address, city, state, pincode);
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Money getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Money basePrice) {
		this.basePrice = basePrice;
	}

	public String getDeliveryPeriod() {
		return deliveryPeriod;
	}

	public void setDeliveryPeriod(String deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * Add image to the list of images attached to the item.
	 * 
	 * @param image
	 *            the new image
	 */
	public void addImage(final Image image) {

		Validate.notNull(image);

		LOGGER.trace("Added image {} to item {}", image, this);

		if (images.isEmpty())
			images = new HashSet<Image>();

		images.add(image);
	}

	public void removeImage(final String code) {
		for (Iterator<Image> iterator = images.iterator(); iterator.hasNext();) {
			Image image = (Image) iterator.next();
			if (image.getCode().equals(code)) {
				iterator.remove();
			}
		}
	}

	public Set<Image> getImages() {
		return Collections.unmodifiableSet(images);
	}

	@Override
	public boolean sameIdentityAs(SpotDealItem other) {
		return other != null && this.code.equals(other.code);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final SpotDealItem other = (SpotDealItem) obj;
		return sameIdentityAs(other);

	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return code;
	}

	// Auto generated surrogate key
	Long id;

	SpotDealItem() {
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
