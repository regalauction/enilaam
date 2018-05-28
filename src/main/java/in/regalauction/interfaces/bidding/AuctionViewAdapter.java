package in.regalauction.interfaces.bidding;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.model.attachment.Document;
import in.regalauction.domain.model.attachment.Image;
import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.Bid;
import in.regalauction.domain.model.auction.EnglishAuction;
import in.regalauction.domain.model.auction.OpenAuction;
import in.regalauction.domain.model.auction.ProxyBid;
import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.interfaces.web.auction.AuctionType;


public final class AuctionViewAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionViewAdapter.class);

	private final Auction auction;
	private final boolean isOpenAuction;
	private final Bid winningBid;
	private final boolean isWinner;
	private final int numBidders;
	private final ProxyBid proxyBid;

	public AuctionViewAdapter(Auction auction, String username) {
		
		LOGGER.trace("Constructing Auction View of {} for {}", auction, username);
		this.auction = auction;
		this.winningBid = auction.getWinningBid();
		
		LOGGER.trace("Checking whether {} is the winner of {}", username, auction);
		this.isWinner = auction.hasWinningBid() && winningBid.getBidder().getUsername().equals(username);
		this.isOpenAuction = auction instanceof OpenAuction;
		
		// Proxy bid can be viewable only by the winner
		proxyBid = (isOpenAuction && isWinner)? ((OpenAuction) auction).getProxyBid(): ProxyBid.NO_BID;
		
		// We need to to avoid the following because it fetches all the users for the auction from the database.
//		LOGGER.trace("Fetching number of users in {}", auction);
//		this.numBidders = auction.getUsers().size();
		numBidders = 0;	// Bidder does not need to know the number of users in the auction
		
		LOGGER.trace("Completed creating the view object");
	}

	public AuctionViewAdapter(Auction auction) {
		
		LOGGER.trace("Constructing Auction View of {}", auction.getAuctionCode());
		this.auction = auction;
		this.winningBid = auction.getWinningBid();
		this.isWinner = false;
		this.isOpenAuction = auction instanceof OpenAuction;
		proxyBid = (isOpenAuction)? ((OpenAuction) auction).getProxyBid(): ProxyBid.NO_BID;
		
		LOGGER.trace("Fetching number of users in {}", auction);
		this.numBidders = auction.getUsers().size();
		
		LOGGER.trace("Completed creating the view object");
	}
	
	public static List<AuctionViewAdapter> toList(final Collection<Auction> auctions) {
		List<AuctionViewAdapter> attachedAuctions = new LinkedList<AuctionViewAdapter>();
		for (Auction auction : auctions) {
			attachedAuctions.add(new AuctionViewAdapter(auction));
		}
		return attachedAuctions;
	}
	
	public static List<AuctionViewAdapter> toList(final Collection<Auction> auctions, final String username) {
		List<AuctionViewAdapter> attachedAuctions = new LinkedList<AuctionViewAdapter>();
		for (Auction auction : auctions) {
			attachedAuctions.add(new AuctionViewAdapter(auction, username));
		}
		return attachedAuctions;
	}


	public Money getNextBidPrice() {
		Validate.isTrue(isOpenAuction);
		return ((OpenAuction) auction).getNextBidPrice();
	}
	
	public Money getBasePrice() {
		Validate.isTrue(isOpenAuction);
		return ((OpenAuction) auction).getBasePrice();
	}
	
	public Money getReservePrice() {
		Validate.isTrue(isOpenAuction);
		return ((OpenAuction) auction).getReservePrice();
	}
	
	public Money getDeltaPrice() {
		Validate.isTrue(isOpenAuction);
		return ((OpenAuction) auction).getDeltaPrice();
	}
	
	public int getTimeExtension() {
		Validate.isTrue(isOpenAuction);
		return ((OpenAuction) auction).getTimeExtension();
	}
	
	public boolean isGoingToEnd() {
		Interval interval = new Interval(DateTime.now(), getEndDate());
		long standardMinutes = interval.toDuration().getStandardMinutes();
		return standardMinutes < getTimeExtension();
	}
	
	public boolean isNoBid() {
		return winningBid.equals(Bid.NO_BID);
	}
	
	public boolean isWinner() {
		return isWinner;
	}
	
	public Bid getWinningBid() {
		return winningBid;
	}
	
	public Money getLeadPrice() {
		return winningBid.getPrice();
	}

	public String getAuctionCode() {
		return auction.getAuctionCode().toString();
	}

	public String getName() {
		return auction.getName();
	}
	
	public Float getQuantity() {
		return auction.getQuantity();
	}

	public DateTime getStartDate() {
		return auction.getStartDate();
	}

	public DateTime getEndDate() {
		return auction.getEndDate();
	}
	
	public Item getItem() {
		return auction.getItem();
	}

	public String getThumbnail() {
		Image thumbnail = auction.getItem().getThumbnail();
		return thumbnail != null? thumbnail.getCode() : "";
	}
	
	public Set<Document> getDocuments() {
		return auction.getDocuments();
	}
	

	public boolean isOpenAuction() {
		return isOpenAuction;
	}
	

	public Set<User> getBidders() {
		return auction.getUsers();
	}

	public int getNumBidders() {
		return numBidders;
	}
	
	public int getNumBids() {
		return auction.getBidCount();
	}
	
	public ProxyBid getProxyBid() {
		return proxyBid;
	}
	
	public AuctionType getAuctionType() {
		return auction instanceof EnglishAuction? AuctionType.ENGLISHOPEN : AuctionType.DUTCHOPEN;
	}
	
	public Set<Image> getImages() {
		return auction.getItem().getImages();
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
