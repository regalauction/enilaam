package in.regalauction.domain.model.spotdeal;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.joda.time.DateTime;

import in.regalauction.domain.model.user.User;
import in.regalauction.domain.shared.ValueObject;

public class SpotDealOrder implements ValueObject<SpotDealOrder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long quantity;
	private DateTime orderTime;
	private User bidder;

	public SpotDealOrder(Long quantity, DateTime orderTime, User bidder) {
		
		Validate.notNull(bidder);
		Validate.isTrue(quantity > 0);
		Validate.notNull(orderTime);
				
		this.quantity = quantity;
		this.orderTime = orderTime;
		this.bidder = bidder;
	}

	public Long getQuantity() {
		return quantity;
	}

	public DateTime getOrderTime() {
		return orderTime;
	}

	public User getBidder() {
		return bidder;
	}

	@Override
	public boolean sameValueAs(SpotDealOrder other) {
		return other != null && new EqualsBuilder().append(this.quantity, other.quantity)
				.append(this.orderTime, other.orderTime).append(this.bidder, other.bidder).isEquals();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final SpotDealOrder other = (SpotDealOrder) obj;

		return sameValueAs(other);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(bidder).append(quantity).append(orderTime).toHashCode();
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName()).append("(").append(bidder).append(", ")
				.append(quantity).append(", ").append(orderTime).append(")").toString();
	}

	// Auto generated surrogate key
	Long id;

	SpotDealOrder() {
		// Needed by Hibernate
	}
}
