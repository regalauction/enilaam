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
public class EnglishAuction extends OpenAuction {
	
	public EnglishAuction(
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
		
		Validate.isTrue(reservePrice.gteq(basePrice));
	}

	@Override
	protected boolean isOutbiddedBy(final Money x, final Money y) {
		return x.lt(y);
	}
	
	@Override
	public Money getNextBidPrice() {
		return hasWinningBid()? getWinningBid().getPrice().plus(deltaPrice): basePrice;
	}

	EnglishAuction() {
		// Needed by Hibernate
	}
}
