package in.regalauction.domain.model.auction;

import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.shared.ValueObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;


/**
 * This class represents a bid in the auction system. Bid has no identity of its own, it is a value object.
 * 
 * @author Diptangshu Chakrabarty
 * @version 1
 * @since 1
 *
 */
public class Bid implements ValueObject<Bid> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Money price;
	protected DateTime bidTime;
	protected User bidder;
	
	public static final Bid NO_BID = new Bid(null, null, null);  
	
	public Bid(Money price, DateTime bidTime, User bidder) {
		this.price = price;
		this.bidTime = bidTime;
		this.bidder = bidder;
	}
	
	public User getBidder() {
		return bidder;
	}
	
	public Money getPrice() {
		return price;
	}
	
	public DateTime getBidTime() {
		return bidTime;
	}

	@Override
	public boolean sameValueAs(final Bid other) {
		return other != null
				&& new EqualsBuilder()
						.append(this.bidder, other.bidder)
						.append(this.price, other.price)
						.append(this.bidTime, other.bidTime)
						.isEquals();
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		final Bid other = (Bid) obj;
		
		return sameValueAs(other);
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(bidder)
		.append(price)
		.append(bidTime)
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName())
			.append("(")
			.append(bidder).append(", ")
			.append(price).append(", ")
			.append(bidTime).append(")")
			.toString();
	}
	
	// Auto generated surrogate key
	Long id;
	Bid() {
		// Needed by Hibernate
	}
}
