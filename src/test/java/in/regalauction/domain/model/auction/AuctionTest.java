package in.regalauction.domain.model.auction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.model.TestHelper;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;


public class AuctionTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionTest.class);
	private Auction auction;
	private User bidder1;
	private User bidder2;
	
	@Before
	public void setUp() throws Exception {
		auction = TestHelper.dummyEnglishAuction();
		bidder1 = TestHelper.dummyBidder("bidder1");
		bidder2 = TestHelper.dummyBidder("bidder2");
		auction.addUser(bidder1);
	}

	@Test
	public void testIsRunning() {
		
		assertTrue("Auction should be running!", auction.isRunning());
		
		auction.setEndDate((new DateTime()).minusMinutes(2));
		assertFalse("Auction should not be running", auction.isRunning());
	}
	
	@Test
	public void testCanBid() {
		try {
			auction.placeBid(new Bid(new Money("110"), new DateTime(), bidder1));
		} catch (NotAttachedException e) {
			fail("Attached bidder is not able to bid!");
		}
		try {
			auction.placeBid(new Bid(new Money("120"), new DateTime(), bidder2));
			fail("Not attached bidder was able to bid!");
		} catch (NotAttachedException e) {
			LOGGER.info("Not attached bidder could not bid - perfect");
		}
	}

	@Test
	public void testHasWinningBid() {
		assertFalse("No winning bid should be present before first bid submission!", auction.hasWinningBid());
		try {
			BidResult bidResult = auction.placeBid(new Bid(new Money("110"), new DateTime(), bidder1));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertTrue("Winning bid should be present after submission of first bid", auction.hasWinningBid());
		} catch (NotAttachedException e) {
			LOGGER.warn("Possible coding error in test case");
			fail("Bidder is not attached to auction!");
		}
		
	}

	@Test
	public void testGetWinner() {
		assertNull("No winner should exist before submission of first bid!", auction.getWinner());
		try {
			BidResult bidResult = auction.placeBid(new Bid(new Money("110"), new DateTime(), bidder1));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Winner should be present after submission of first bid", "bidder1", auction.getWinner().getUsername());
		} catch (NotAttachedException e) {
			LOGGER.warn("Possible coding error in test case");
			fail("Bidder is not attached to auction!");
		}
	}

}
