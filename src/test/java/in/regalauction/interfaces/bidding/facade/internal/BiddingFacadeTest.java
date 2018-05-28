package in.regalauction.interfaces.bidding.facade.internal;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import in.regalauction.domain.model.TestHelper;
import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.AuctionCode;
import in.regalauction.domain.model.auction.AuctionRepository;
import in.regalauction.domain.model.auction.BidResult;
import in.regalauction.domain.model.auction.EnglishAuction;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserRepository;


public class BiddingFacadeTest {
	
	private static final EnglishAuction ENGLISHAUCTION_1 = TestHelper.dummyEnglishAuction();
	private static final User USER_1 = TestHelper.dummyBidder("zubin");
	
	BiddingFacadeImpl biddingFacade;
	UserRepository userRepository;
	AuctionRepository auctionRepository;

	@Before
	public void setUp() throws Exception {
		userRepository = createMock(UserRepository.class);
		auctionRepository = createMock(AuctionRepository.class);
		// TODO how to use autowire with test cases
		biddingFacade = new BiddingFacadeImpl(userRepository, auctionRepository);
	}

	@After
	public void tearDown() throws Exception {
		verify(userRepository, auctionRepository);
	}

	@Test
	public void testPlaceBid() throws Exception {
		
		User expectedUser = USER_1;
		Auction expectedAuction = ENGLISHAUCTION_1;
		expectedAuction.addUser(expectedUser);
		
		AuctionCode auctionCode = new AuctionCode("1");
		
		expect(userRepository.findByUsername("zubin")).andReturn(expectedUser).atLeastOnce();
		expect(auctionRepository.findByAuctionCode(auctionCode)).andReturn(expectedAuction).atLeastOnce();
		
		replay(userRepository, auctionRepository);
		
		BidResult bidResult;
		
		bidResult = biddingFacade.placeBid("zubin", "1", new Money("100"), new DateTime(), false);
		assertEquals("First bid submission failed!", BidResult.ACCEPTED, bidResult);
		
		bidResult = biddingFacade.placeBid("zubin", "1", new Money("105"), new DateTime(), false);
		assertEquals("The bid was less than next bid, yet it was accepted!", BidResult.REJECTED_OUTBID, bidResult);
		
		bidResult = biddingFacade.placeBid("zubin", "1", new Money("110"), new DateTime(), false);
		assertEquals("Second bid submission failed!", BidResult.ACCEPTED, bidResult);
		
		bidResult = biddingFacade.placeBid("zubin", "1", new Money("125"), new DateTime(), false);
		assertEquals("Bid increment was not multiple of delta, yet it was accepted!", BidResult.REJECTED_NOTMULTIPLE, bidResult);
		
		bidResult = biddingFacade.placeBid("zubin", "1", new Money("140"), new DateTime(), false);
		assertEquals("Bid higher than next bid price submission failed!", BidResult.ACCEPTED, bidResult);
	}

}
