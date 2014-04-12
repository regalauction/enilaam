package in.regalauction.domain.model.auction;

import in.regalauction.domain.shared.ValueObject;


public enum BidResult implements ValueObject<BidResult> {

	ACCEPTED,
	REJECTED_OUTBID,
	REJECTED_NOTMULTIPLE, 
	REJECTED_TIMEENDED, 
	REJECTED_NOTSTARTED;

	@Override
	public boolean sameValueAs(final BidResult other) {
		return other != null && this.equals(other);
	}
	
}
