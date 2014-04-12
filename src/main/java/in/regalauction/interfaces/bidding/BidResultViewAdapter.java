package in.regalauction.interfaces.bidding;

import in.regalauction.domain.model.auction.BidResult;

public final class BidResultViewAdapter {
	
	private boolean isError;
	private final String code;
	
	public BidResultViewAdapter(final BidResult bidResult) {
		this.isError = bidResult != BidResult.ACCEPTED;
		this.code = bidResult.toString();
	}
	
	
	public String getCode() {
		return code;
	}

	public boolean isError() {
		return isError;
	}

}
