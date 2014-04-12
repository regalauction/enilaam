package in.regalauction.domain.model.auction;


/**
 * Represents an auction of type Sealed-Bid Auction.
 * 
 * <p><b>Logic:</b></p> In a sealed-bid auction, the users cannot see the winning bid.
 * There can be atmost one winner.
 * 
 * @author Diptangshu Chakrabarty
 *
 */
public class SealedBidAuction extends Auction {

	@Override
	public Bid getWinningBid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BidResult addBid(Bid bid) {
		// TODO Auto-generated method stub
		return null;
	}

}
