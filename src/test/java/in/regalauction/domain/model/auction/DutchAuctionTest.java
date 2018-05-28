package in.regalauction.domain.model.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.model.TestHelper;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;


public class DutchAuctionTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnglishAuction.class);
	private DutchAuction dutchAuction;
	private User bidder1;

	@Before
	public void setUp() throws Exception {
		dutchAuction = TestHelper.dummyDutchAuction();
		bidder1 = TestHelper.dummyBidder("zubin");
		dutchAuction.addUser(bidder1);
	}

	@Test
	public void testFirstBid() {
		
		BidResult bidResult;

		try {
			LOGGER.info("Testing first bid");
			bidResult = dutchAuction.placeBid(new Bid(new Money("11000"), new DateTime(), bidder1));
			assertEquals("First bid was higher than base price, but was accepted!", BidResult.REJECTED_OUTBID, bidResult);
			
			bidResult = dutchAuction.placeBid(new Bid(new Money("9000"), new DateTime(), bidder1));
			assertEquals("Failed to place first bid", BidResult.ACCEPTED, bidResult);
			
		} catch (NotAttachedException e) {
			LOGGER.warn("There seems to be some problem with the test case!");
			fail("Bidder attachment check failed.");
		}
	}
	
	@Test
	public void testSecondBid() {
		
		BidResult bidResult;

		try {
			testFirstBid();
			LOGGER.info("Testing Second bid");
			
			
			bidResult = dutchAuction.placeBid(new Bid(new Money("9900"), new DateTime(), bidder1));
			assertEquals("Bid was greater than next bid price, but was accepted!", BidResult.REJECTED_OUTBID, bidResult);
			
			bidResult = dutchAuction.placeBid(new Bid(new Money("8796"), new DateTime(), bidder1));
			assertEquals("Bid was not in multiple of delta price, but was accepted!", BidResult.REJECTED_NOTMULTIPLE, bidResult);
			
			bidResult = dutchAuction.placeBid(new Bid(new Money("8700"), new DateTime(), bidder1));
			assertEquals("Bid was in multiple of delta price, but was not accepted!", BidResult.ACCEPTED, bidResult);
		} catch (NotAttachedException e) {
			LOGGER.warn("There seems to be some problem with the test case!");
			fail("Auction attachment check failed.");
		}
	}

}
