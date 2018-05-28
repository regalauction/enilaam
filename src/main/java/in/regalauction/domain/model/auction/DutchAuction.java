package in.regalauction.domain.model.auction;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;

import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.types.Money;


/**
 * This class represents a Enlish open auction.
 * 
 * <p><b>Logic:</b> In a English auction (or Forward auction) the bidder who has submitted the highest bid wins.
 * @author Diptangshu Chakrabarty
 * @version 1
 * @since 1
 *
 */
public class DutchAuction extends OpenAuction {
	
	public DutchAuction(
			final AuctionCode auctionCode, 
			final String name, 
			final Item item,
			final Money basePrice, 
			final Money reservePrice, 
			final Money deltaPrice,
			final DateTime startDate, 
			final DateTime endDate, 
			final Integer timeExtension) {
		
		super(auctionCode, name, item, basePrice, reservePrice, deltaPrice, startDate,
				endDate, timeExtension);
		
		Validate.isTrue(reservePrice.lteq(basePrice));
	}

	@Override
	protected boolean isOutbiddedBy(Money x, Money y) {
		return x.gt(y);
	}
	
	@Override
	public Money getNextBidPrice() {
		// TODO what will happen when bid price decrements to less than 0?
		return hasWinningBid()? getWinningBid().getPrice().minus(deltaPrice): basePrice;
	}
	
	DutchAuction() {
		// Needed by Hibernate
	}

}
