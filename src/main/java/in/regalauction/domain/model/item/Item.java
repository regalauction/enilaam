package in.regalauction.domain.model.item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.shared.Entity;



/**
 * This class represents an item.
 * 
 * @author Diptangshu Chakrabarty
 * @since 1
 *
 */
public class Item implements Entity<Item> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Item.class);
	
	private String code;
	private String name;
	private String unitOfMeasure;
	private Image thumbnail;
	private Set<Image> images = Collections.emptySet();
	
	private String field1;
	private String field2;
	private String field3;
	
	
	public Item(final String code, final String name) {
		Validate.notEmpty(code);
		Validate.notNull(name);
		this.code = code;
		this.name = name;
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
	
	public String getField1() {
		return field1;
	}
	
	public String getField2() {
		return field2;
	}
	
	public String getField3() {
		return field3;
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
	
	public void setField1(String field1) {
		this.field1 = field1;
	}
	
	public void setField2(String field2) {
		this.field2 = field2;
	}
	
	public void setField3(String field3) {
		this.field3 = field3;
	}
	
	/**
	 * Add image to the list of images attached to the item.
	 * @param image the new image
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
	public boolean sameIdentityAs(Item other) {
		return other != null && this.code.equals(other.code);
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		final Item other = (Item) obj;
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
	Item() {
		// Needed by Hibernate
	}
}
