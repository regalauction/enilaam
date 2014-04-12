package in.regalauction.domain.model.auction;

import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;

import org.joda.time.DateTime;


public class ProxyBid extends Bid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final ProxyBid NO_BID = new ProxyBid(Bid.NO_BID.getPrice(), Bid.NO_BID.getBidTime(), Bid.NO_BID.getBidder());

	public ProxyBid(Money price, DateTime bidTime, User bidder) {
		super(price, bidTime, bidder);
	}
	
	/**
	 * @return a Bid object that can be added to the list of bids for the auction.
	 */
	public Bid getBid() {
		return new Bid(price, bidTime, bidder);
	}
	
	ProxyBid() {
		// Needed by Hibernate
	}
}
