package in.regalauction.domain.model.auction;

import in.regalauction.domain.shared.ValueObject;

import org.apache.commons.lang.Validate;


public class AuctionCode implements ValueObject<AuctionCode> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	public AuctionCode(final String id) {
		Validate.notEmpty(id);
		this.id = id;
	}

	@Override
	public boolean sameValueAs(AuctionCode other) {
		return other != null && this.id.equals(other.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;

	    AuctionCode other = (AuctionCode) obj;

	    return sameValueAs(other);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return id;
	}
	
	AuctionCode() {
		// Needed by Hibernate
	}
}
