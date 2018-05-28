package in.regalauction.domain.model.auction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.types.Money;



/**
 * Represents an auction of type Open Auction. 
 * 
 * <p><b>Logic:</b> In an open auction, the users can see the winning bid. There can be 
 * atmost one winner.
 * 
 * <p>Open Auction is an abstract class. This should be extended to implement the functionalities
 * specific to a particular auction logic, such as determining the winning bid.
 * @author Diptangshu Chakrabarty
 * @version 1
 * @since 1
 *
 */
public abstract class OpenAuction extends Auction {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OpenAuction.class);

	protected Money basePrice;
	protected Money reservePrice;
	protected Money deltaPrice;
	protected Integer timeExtension;
	
	protected List<ProxyBid> proxyBids = new ArrayList<ProxyBid>();
	
	protected OpenAuction(
			final AuctionCode auctionCode,
			final String name,
			final Item item,
			final Money basePrice,
			final Money reservePrice,
			final Money deltaPrice,
			final DateTime startDate,
			final DateTime endDate,
			final Integer timeExtension) {
		
		super(auctionCode, item, name, startDate, endDate);
		
		Validate.notNull(basePrice);
		Validate.notNull(reservePrice);
		Validate.notNull(deltaPrice);
		Validate.notNull(timeExtension);
		Validate.isTrue(basePrice.gteq(Money.ZERO));
		Validate.isTrue(reservePrice.gteq(Money.ZERO));
		Validate.isTrue(deltaPrice.gteq(Money.ZERO));
		Validate.isTrue(timeExtension >= 0);
		
		this.name = name;
		this.item = item;
		this.basePrice = basePrice;
		this.reservePrice = reservePrice;
		this.deltaPrice = deltaPrice;
		this.timeExtension = timeExtension;
		
	}
	
	/**
	 * Computes the next acceptable bid price.
	 * @return next bid price
	 */
	public abstract Money getNextBidPrice();
	
	@Override
	protected BidResult addBid(final Bid bid) {
		
		Validate.notNull(bid);
		
		LOGGER.trace("Start Date: {}, Bid Time: {}", startDate, bid.getBidTime());
		
		if (bid.getBidTime().isBefore(startDate))
			return BidResult.REJECTED_NOTSTARTED;
		
		if (bid.getBidTime().isAfter(endDate))			
			return BidResult.REJECTED_TIMEENDED;
		
		Money bidPrice = bid.getPrice();
		
		if (isOutbidNextBid(bidPrice))
			return BidResult.REJECTED_OUTBID;
		
		if (hasWinningBid() && !checkMultiple(bidPrice))
			return BidResult.REJECTED_NOTMULTIPLE;
		
		ProxyBid proxyBid = getCurrentProxyBid();
		
		if (bid instanceof ProxyBid) {
			
			// Placed bid is a proxy bid
			
			if (!hasProxyBid()) {
				
				LOGGER.debug("No Existing proxy bid");
				
				// add auto bid
				Bid newBid = new Bid(getNextBidPrice(), new DateTime(), bid.getBidder());
				LOGGER.debug("Automatically adding {} as proxy bid", newBid);
				bids.add(newBid);
				
				proxyBids.add((ProxyBid) bid);
				
			} else if (bidPrice.lt(proxyBid.getPrice())) {
					
				LOGGER.debug("New Proxy bid is less than the old one");
				
				// Add bid with new proxy bid on behalf of this bidder
				Bid autoBid = ((ProxyBid) bid).getBid();
				LOGGER.debug("Automatically adding {} for new proxy bid", autoBid);
				bids.add(autoBid);
				
				// Add bid with next bid price on behalf of old bidder
				Bid autoBid2 = new Bid(getNextBidPrice(), new DateTime(), proxyBid.getBidder());
				LOGGER.debug("Automatically adding {} for old proxy bid", autoBid2);
				bids.add(autoBid2);
				
			} else if (bidPrice.gt(proxyBid.getPrice())) {
				
				LOGGER.debug("New Proxy bid is greater than the old one");
				
				// Add bid with old proxy bid on behalf of old bidder if lead price has not out bidded that amount
				Money oldProxyBidPrice = proxyBid.getPrice();
				if (!isOutbidNextBid(oldProxyBidPrice)) {
					Bid autoBid1 = new Bid(oldProxyBidPrice, new DateTime(), proxyBid.getBidder());
					LOGGER.debug("Automatically adding {} for old proxy bid", autoBid1);
					bids.add(autoBid1);
				}
				
				// Add bid with next bid price on behalf of this bidder
				Bid autoBid2 = new Bid(getNextBidPrice(), new DateTime(), bid.getBidder());
				LOGGER.debug("Automatically adding {} as new proxy bid", autoBid2);
				bids.add(autoBid2);
				
				proxyBids.add((ProxyBid) bid);
				
			} else {
				
				LOGGER.debug("New Proxy bid is equal to the old one");
				
				// Add bid with new proxy bid on behalf of this bidder
				Bid autoBid1 = ((ProxyBid) bid).getBid();
				LOGGER.debug("Automatically adding {} for new proxy bid", autoBid1);
				bids.add(autoBid1);
				
				// Add bid with same bid price on behalf of old bidder
				Bid autoBid2 = new Bid(bid.getPrice(), new DateTime(), proxyBid.getBidder());
				LOGGER.debug("Automatically adding {} for old proxy bidder", autoBid2);
				bids.add(autoBid2);
				
			}
			
		} else {
			
			// Placed bid is not a Proxy
			
			bids.add(bid);
	
			// add proxy bids
			if (isOutbidProxy(bidPrice)) {
				Money nextBidPrice = getNextBidPrice();
				Bid autoBid = new Bid(nextBidPrice, new DateTime(), proxyBid.getBidder());
				LOGGER.debug("Automatically adding {} as proxy bid", autoBid);
				bids.add(autoBid);
			}
		}
			
		updateEndTime(bid);
		
		return BidResult.ACCEPTED;
	}

	private ProxyBid getCurrentProxyBid() {
		return hasProxyBid() ? proxyBids.get(proxyBids.size() - 1) : ProxyBid.NO_BID;
	}

	private void updateEndTime(final Bid bid) {
		if (bid.getBidTime().isAfter(endDate.minusMinutes(timeExtension))) {
			LOGGER.debug("Extending end date of {} by {} minutes", auctionCode, timeExtension);
			endDate = endDate.plusMinutes(timeExtension);
		}
	}
	
	
	private boolean checkMultiple(final Money bidPrice) {
		
		Validate.notNull(bidPrice);
			
		Money nextBidPrice = getNextBidPrice();
		Money priceDiff = Money.abs(bidPrice.minus(nextBidPrice));
		
		LOGGER.debug("Price difference with nextBidPrice: {}", priceDiff);
		
		return priceDiff.eq(Money.ZERO) || priceDiff.isMultipleOf(deltaPrice);
	}
	
	@Override
	public Bid getWinningBid() {
		return !hasWinningBid()? Bid.NO_BID : bids.get(bids.size() - 1);
	}
	
	public ProxyBid getProxyBid() {
		return hasProxyBid()? proxyBids.get(proxyBids.size()-1): ProxyBid.NO_BID;
	}
	
	private boolean isOutbidNextBid(final Money bidPrice) {
		return isOutbiddedBy(bidPrice, getNextBidPrice());
	}
	
	private boolean isOutbidProxy(final Money bidPrice) {
		return hasProxyBid() && isOutbiddedBy(bidPrice, getCurrentProxyBid().getPrice());
	}
	
	private boolean hasProxyBid() {
		return !proxyBids.isEmpty();
	}
	
	
	protected abstract boolean isOutbiddedBy(Money x, Money y);
	
	public Money getBasePrice() {
		return basePrice;
	}
	public Money getReservePrice() {
		return reservePrice;
	}
	public Money getDeltaPrice() {
		return deltaPrice;
	}
	public Integer getTimeExtension() {
		return timeExtension;
	}
	
	public void setBasePrice(Money basePrice) {
		this.basePrice = basePrice;
	}

	public void setReservePrice(Money reservePrice) {
		this.reservePrice = reservePrice;
	}

	public void setDeltaPrice(Money deltaPrice) {
		this.deltaPrice = deltaPrice;
	}

	public void setTimeExtension(Integer timeExtension) {
		this.timeExtension = timeExtension;
	}

	OpenAuction() {
		// Needed by Hibernate
	}

}
