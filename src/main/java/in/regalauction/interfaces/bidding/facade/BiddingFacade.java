package in.regalauction.interfaces.bidding.facade;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.security.access.prepost.PreAuthorize;

import in.regalauction.domain.model.auction.BidResult;
import in.regalauction.domain.model.auction.CannotBidException;
import in.regalauction.domain.model.auction.NotAttachedException;
import in.regalauction.domain.model.types.Money;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;


public interface BiddingFacade {

	@PreAuthorize("hasRole('ROLE_BIDDER')")
	public BidResult placeBid(String username, String auctionCode, Money bidPrice, DateTime bidTime, boolean proxy) throws CannotBidException;
	
	public Collection<AuctionViewAdapter> fetchRunningAuctions(String username);
	
	public AuctionViewAdapter fetchAuction(String username, String auctionCode) throws NotAttachedException;
	
	public Collection<AuctionViewAdapter> fetchUpcomingAuctions();
	
	public Collection<AuctionViewAdapter> fetchOldAuctions(String username);
}
