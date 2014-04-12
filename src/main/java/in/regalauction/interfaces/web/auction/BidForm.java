package in.regalauction.interfaces.web.auction;

import in.regalauction.domain.model.types.Money;

import java.util.Map;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;


public class BidForm {

	@NotEmpty
	private String[] auctionCodes;
	@Valid
	private Map<String, AuctionRow> auctions;

	public Money getBidPrice(String auctionId) {
		return new Money(auctions.get(auctionId).getBidPrice());
	}
	
	public boolean isProxy(String auctionId) {
		return auctions.get(auctionId).isProxy();
	}

	public String[] getAuctionCodes() {
		return auctionCodes;
	}

	public void setAuctionCodes(String[] auctionCodes) {
		this.auctionCodes = auctionCodes;
	}

	public Map<String, AuctionRow> getAuctions() {
		return auctions;
	}

	public void setAuctions(Map<String, AuctionRow> auctions) {
		this.auctions = auctions;
	}

}
