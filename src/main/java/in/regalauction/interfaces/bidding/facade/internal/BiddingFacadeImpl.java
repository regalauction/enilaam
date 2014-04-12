package in.regalauction.interfaces.bidding.facade.internal;

import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.AuctionCode;
import in.regalauction.domain.model.auction.AuctionRepository;
import in.regalauction.domain.model.auction.Bid;
import in.regalauction.domain.model.auction.BidResult;
import in.regalauction.domain.model.auction.CannotBidException;
import in.regalauction.domain.model.auction.NotAttachedException;
import in.regalauction.domain.model.auction.OpenAuction;
import in.regalauction.domain.model.auction.ProxyBid;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserRepository;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;
import in.regalauction.interfaces.bidding.facade.BiddingFacade;

import java.util.Collection;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class BiddingFacadeImpl implements BiddingFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(BiddingFacadeImpl.class);
	
	private final UserRepository userRepository;
	private final AuctionRepository auctionRepository;

	public BiddingFacadeImpl(UserRepository userRepository,
			AuctionRepository auctionRepository) {
		this.userRepository = userRepository;
		this.auctionRepository = auctionRepository;
	}

	@Override
	public BidResult placeBid(final String username, final String auctionCode, final Money bidPrice, final DateTime bidTime, final boolean proxy) throws CannotBidException {
		
		LOGGER.trace("placeBid()");
		
		final User bidder = userRepository.findByUsername(username);
		final Auction auction = auctionRepository.findByAuctionCode(new AuctionCode(auctionCode));
		
		if (proxy && !(auction instanceof OpenAuction))
			throw new IllegalArgumentException("Cannot place proxy bid on this type of auction.");
		
		Bid bid = proxy? new ProxyBid(bidPrice, bidTime, bidder) : new Bid(bidPrice, bidTime, bidder);
		BidResult placeBid;
		
		try {
			placeBid = auction.placeBid(bid);
		} catch (NotAttachedException e) {
			throw new CannotBidException();
		}
		
		return placeBid;
	}
	
	@Override
	public Collection<AuctionViewAdapter> fetchRunningAuctions(final String username) {
		LOGGER.trace("Fetching all running auctions for {}", username);
		return AuctionViewAdapter.toList(auctionRepository.findRunningAuctions(username), username);
	}

	@Override
	public Collection<AuctionViewAdapter> fetchOldAuctions(final String username) {
		LOGGER.trace("Fetching auction history of {}", username);
		return AuctionViewAdapter.toList(auctionRepository.findOldAuctions(username), username);
	}
	
	@Override
	public Collection<AuctionViewAdapter> fetchUpcomingAuctions() {
		LOGGER.trace("Fetching upcomming auctions");
		return AuctionViewAdapter.toList(auctionRepository.findUpcommingAuctions());
	}

	@Override
	public AuctionViewAdapter fetchAuction(final String username, final String auctionCode) throws NotAttachedException {
		
		LOGGER.trace("Fetching auction {} for user {}", auctionCode, username);
		
		Auction auction = auctionRepository.findByAuctionCode(new AuctionCode(auctionCode));
		
		if (!auction.isAttached(userRepository.findByUsername(username)))
			throw new NotAttachedException();
		
		return new AuctionViewAdapter(auction, username);
	}

}
