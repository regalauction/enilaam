package in.regalauction.domain.model.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import in.regalauction.domain.model.TestHelper;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EnglishAuctionTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnglishAuction.class);
	private EnglishAuction englishAuction;
	private User bidder1;

	@Before
	public void setUp() throws Exception {
		englishAuction = TestHelper.dummyEnglishAuction();
		bidder1 = TestHelper.dummyBidder("zubin");
		englishAuction.addUser(bidder1);
	}

	@Test
	public void testFirstBid() {
		
		BidResult bidResult;

		try {
			LOGGER.info("Testing first bid");
			bidResult = englishAuction.placeBid(new Bid(new Money("90"), new DateTime(), bidder1));
			assertEquals("First bid was lower than base price, but was accepted!", BidResult.REJECTED_OUTBID, bidResult);
			
			bidResult = englishAuction.placeBid(new Bid(new Money("110"), new DateTime(), bidder1));
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
			
			
			bidResult = englishAuction.placeBid(new Bid(new Money("115"), new DateTime(), bidder1));
			assertEquals("Bid was less than next bid price, but was accepted!", BidResult.REJECTED_OUTBID, bidResult);
			
			bidResult = englishAuction.placeBid(new Bid(new Money("125"), new DateTime(), bidder1));
			assertEquals("Bid was not in multiple of delta price, but was accepted!", BidResult.REJECTED_NOTMULTIPLE, bidResult);
			
			bidResult = englishAuction.placeBid(new Bid(new Money("130"), new DateTime(), bidder1));
			assertEquals("Bid was in multiple of delta price, but was not accepted!", BidResult.ACCEPTED, bidResult);
		} catch (NotAttachedException e) {
			LOGGER.warn("There seems to be some problem with the test case!");
			fail("Auction attachment check failed.");
		}
	}

}
