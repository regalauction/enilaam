package in.regalauction.domain.model.auction;

import static org.junit.Assert.assertEquals;
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


public class OpenAuctionTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenAuctionTest.class);
	private OpenAuction openAuction;
	private User zubin;
	private User deep;
	
	@Before
	public void setUp() throws Exception {
		LOGGER.info("Setting up Test");
		openAuction = TestHelper.dummyEnglishAuction();
		zubin = TestHelper.dummyBidder("zubin");
		deep = TestHelper.dummyBidder("deep");
		openAuction.addUser(zubin);
		openAuction.addUser(deep);
	}

	@Test
	public void testTimeExtension() {
		try {
			DateTime bidTime;
			BidResult bidResult;
			
			openAuction.setTimeExtension(3);
			LOGGER.info("Testing with {} minutes Time Extension", openAuction.getTimeExtension());
			
			LOGGER.info("Testing bid submission 1 second before time extension window");
			openAuction.setEndDate((new DateTime()).plusMinutes(5));
			DateTime oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.minusMinutes(openAuction.getTimeExtension()).minusSeconds(1);
			bidResult = openAuction.placeBid(new Bid(new Money("110"), bidTime, zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Time should not be extended when bid submission is before time extension window", oldEndDate, openAuction.getEndDate());
			
			LOGGER.info("Testing bid submission within time extension window");
			oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.minusMinutes(openAuction.getTimeExtension()).plusSeconds(1);
			bidResult = openAuction.placeBid(new Bid(new Money("120"), bidTime, zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Time should be extended when bid submission is within time extension window", 
					oldEndDate.plusMinutes(openAuction.getTimeExtension()), openAuction.getEndDate());
			
			LOGGER.info("Testing proxy bid submission within time extension window");
			oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.minusMinutes(openAuction.getTimeExtension()).plusSeconds(1);
			bidResult = openAuction.placeBid(new ProxyBid(new Money("500"), bidTime, zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Time should be extended when proxy bid submission is within time extension window", 
					oldEndDate.plusMinutes(openAuction.getTimeExtension()), openAuction.getEndDate());
			
			LOGGER.info("Testing bid submission 1 second before time extension window");
			oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.minusSeconds(1);
			bidResult = openAuction.placeBid(new Bid(new Money("140"), bidTime, zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Time should be extended when bid submission is within time extension window", 
					oldEndDate.plusMinutes(openAuction.getTimeExtension()), openAuction.getEndDate());
			
			LOGGER.info("Testing bid submission after time end");
			oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.plusSeconds(1);
			bidResult = openAuction.placeBid(new Bid(new Money("150"), bidTime, zubin));
			assertEquals("Bid should not be accepted after time end", BidResult.REJECTED_TIMEENDED, bidResult);
			assertEquals("Time should be extended when bid is submitted after auction end", oldEndDate, openAuction.getEndDate());
			
			LOGGER.info("Testing time extension when invalid bid is submitted");
			oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.minusSeconds(1);
			bidResult = openAuction.placeBid(new Bid(new Money("130"), bidTime, zubin));
			assertEquals("Bid less than lead price should not accepted", BidResult.REJECTED_OUTBID, bidResult);
			assertEquals("Time should not be extended when bid is invalid", oldEndDate, openAuction.getEndDate());
			
			openAuction.setTimeExtension(0);
			LOGGER.info("Testing with {} minutes Time Extension", openAuction.getTimeExtension());
			
			LOGGER.info("Testing bid submission within time extension window");
			oldEndDate = openAuction.getEndDate();
			bidTime = oldEndDate.minusSeconds(1);
			bidResult = openAuction.placeBid(new Bid(new Money("160"), bidTime, zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Time should not be extended when time extension is 0 for the Auction", oldEndDate, openAuction.getEndDate());
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	public void testBidDonotOutbidProxy() {
		try {
			// register proxy bid
			LOGGER.info("Proxy bid higher than next bid price");
			openAuction.placeBid(new ProxyBid(openAuction.getBasePrice().plus(new Money("500")), new DateTime(), zubin));
			
			Money bidPrice = openAuction.getBasePrice().plus(openAuction.getDeltaPrice());
			BidResult bidResult = openAuction.placeBid(new Bid(bidPrice, new DateTime(), deep));
			assertEquals(BidResult.ACCEPTED, bidResult);
			
			Bid winningBid = openAuction.getWinningBid();
			assertTrue("Proxy bid should be added", winningBid.getPrice().eq(bidPrice.plus(openAuction.getDeltaPrice())) && winningBid.getBidder().equals(zubin));
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}

	
	@Test
	public void testBidOutbidProxy() {
		try {
			// register proxy bid
			LOGGER.info("Proxy bid higher than next bid price");
			ProxyBid proxyBid = new ProxyBid(openAuction.getBasePrice().plus(new Money("500")), new DateTime(), zubin);
			openAuction.placeBid(proxyBid);
			
			Money bidPrice = proxyBid.getPrice().plus(openAuction.getDeltaPrice());
			BidResult bidResult = openAuction.placeBid(new Bid(bidPrice, new DateTime(), deep));
			assertEquals(BidResult.ACCEPTED, bidResult);
			
			assertTrue("Proxy bid should not be added", openAuction.getWinningBid().getPrice().eq(bidPrice) 
					&& openAuction.getWinner().equals(deep));
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	public void testFirstBidWhilePlacingProxy() {
		BidResult bidResult;
		try {
			LOGGER.info("Placing proxy bid as first bid");
			bidResult = openAuction.placeBid(new ProxyBid(new Money("500"), new DateTime(), zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Automatic bid should be added", openAuction.getBasePrice(), openAuction.getWinningBid().getPrice());
			
			LOGGER.info("Placing invalid proxy bid as first bid");
			Money oldWinningPrice = openAuction.getWinningBid().getPrice();
			bidResult = openAuction.placeBid(new ProxyBid(new Money("679"), new DateTime(), zubin));
			assertEquals(BidResult.REJECTED_NOTMULTIPLE, bidResult);
			assertEquals("No automatic bid should be added", oldWinningPrice, openAuction.getWinningBid().getPrice());
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	/**
	 * Bidder1 places first normal bid, then Bidder2 places proxy bid
	 */
	public void testAutoBidWhilePlacingProxy() {

		ProxyBid proxyBid;
		BidResult bidResult;
		Money oldWinningBidPrice;
		
		try {
			// first bid
			openAuction.placeBid(new Bid(openAuction.getBasePrice(), new DateTime(), deep));
			
			Money leadPrice = openAuction.getWinningBid().getPrice();
			Money nextBidPrice = openAuction.getNextBidPrice();
			
			LOGGER.info("Proxy bid less than lead price {}", leadPrice);
			oldWinningBidPrice = openAuction.getWinningBid().getPrice();
			proxyBid = new ProxyBid(leadPrice.minus(openAuction.getDeltaPrice()), new DateTime(), zubin);
			bidResult = openAuction.placeBid(proxyBid);
			assertEquals("Proxy Bid should be rejected when less than lead price", BidResult.REJECTED_OUTBID, bidResult);
			assertEquals("No automatic bid should be added", oldWinningBidPrice, openAuction.getWinningBid().getPrice());
			
			LOGGER.info("Proxy bid equals to lead price {}", leadPrice);
			oldWinningBidPrice = openAuction.getWinningBid().getPrice();
			proxyBid = new ProxyBid(leadPrice, new DateTime(), zubin);
			bidResult = openAuction.placeBid(proxyBid);
			assertEquals("Proxy Bid should be rejected when equal to lead price", BidResult.REJECTED_OUTBID, bidResult);
			assertEquals("No automatic bid should be added", oldWinningBidPrice, openAuction.getWinningBid().getPrice());
			
			LOGGER.info("Proxy bid less than next bid price {}", nextBidPrice);
			oldWinningBidPrice = openAuction.getWinningBid().getPrice();
			proxyBid = new ProxyBid(nextBidPrice.minus(openAuction.getDeltaPrice().divide(2)), new DateTime(), zubin);
			bidResult = openAuction.placeBid(proxyBid);
			assertEquals("Proxy bid should be rejected when less than next bid price", BidResult.REJECTED_OUTBID, bidResult);
			assertEquals("No automatic bid should be added", oldWinningBidPrice, openAuction.getWinningBid().getPrice());
			
			LOGGER.info("Proxy bid equals to next bid price {}", nextBidPrice);
			proxyBid = new ProxyBid(nextBidPrice, new DateTime(), zubin);
			bidResult = openAuction.placeBid(proxyBid);
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Automatic bid should be added", nextBidPrice, openAuction.getWinningBid().getPrice());

			Money oldWinningBid = openAuction.getWinningBid().getPrice();
			nextBidPrice = openAuction.getNextBidPrice();
			LOGGER.info("Proxy bid higher than next bid price but not multiple {}", nextBidPrice);
			proxyBid = new ProxyBid(nextBidPrice.plus(openAuction.getDeltaPrice().divide(2)), new DateTime(), zubin);
			bidResult = openAuction.placeBid(proxyBid);
			assertEquals(BidResult.REJECTED_NOTMULTIPLE, bidResult);
			assertEquals("Automatic bid should not be added", oldWinningBid, openAuction.getWinningBid().getPrice());
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	public void testAutoBid() {
		BidResult bidResult;
		try {
			// Proxy bid
			Money proxyBidPrice = new Money("550");
			openAuction.placeBid(new ProxyBid(proxyBidPrice, new DateTime(), deep));
			assertEquals(openAuction.getWinningBid().getPrice(), openAuction.getBasePrice());
			
			LOGGER.info("Bid less than proxy bid {}", proxyBidPrice);
			Money bidPrice = openAuction.getNextBidPrice();
			bidResult = openAuction.placeBid(new Bid(bidPrice, new DateTime(), zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Automatic bid should be added with next bid price", bidPrice.plus(openAuction.getDeltaPrice()), openAuction.getWinningBid().getPrice());
			assertEquals("Automatic bid should be added on behalf of proxy bidder", deep, openAuction.getWinner());
			
			LOGGER.info("Bid equal to proxy bid {}", proxyBidPrice);
			bidResult = openAuction.placeBid(new Bid(proxyBidPrice, new DateTime(), zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("No automatic bid should be added", proxyBidPrice, openAuction.getWinningBid().getPrice());
			assertEquals("No automatic bid should be added", zubin, openAuction.getWinner());
			
			LOGGER.info("Bid greater than proxy bid {}", proxyBidPrice);
			bidPrice = proxyBidPrice.plus(openAuction.getDeltaPrice());
			bidResult = openAuction.placeBid(new Bid(bidPrice, new DateTime(), zubin));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals(zubin, openAuction.getWinner());
			assertEquals("No automatic bid should be added", bidPrice, openAuction.getWinningBid().getPrice());
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	public void testGetProxyBid() {
		
		try {
			ProxyBid proxyBid = new ProxyBid(new Money("550"), new DateTime(), zubin);
			openAuction.placeBid(proxyBid);
			assertEquals(proxyBid, openAuction.getProxyBid());
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	public void testSecondProxyLowerThanFirst() {
		BidResult bidResult;
		try {
			// first proxy
			Money proxyBidPrice = new Money("550");
			openAuction.placeBid(new ProxyBid(proxyBidPrice, new DateTime(), zubin));
			
			// second proxy
			LOGGER.info("difference between second proxy and first proxy is greater than delta price, not multiple.");
			Money secondProxyBidPrice = new Money("305");
			Money oldWinningBid = openAuction.getWinningBid().getPrice();
			bidResult = openAuction.placeBid(new ProxyBid(secondProxyBidPrice, new DateTime(), deep));
			assertEquals(BidResult.REJECTED_NOTMULTIPLE, bidResult);
			assertEquals("Auto bid should not be placed", oldWinningBid, openAuction.getWinningBid().getPrice());
			
			LOGGER.info("difference between second proxy and first proxy is less than delta price, not multiple");
			secondProxyBidPrice = new Money("545");
			oldWinningBid = openAuction.getWinningBid().getPrice();
			bidResult = openAuction.placeBid(new ProxyBid(secondProxyBidPrice, new DateTime(), deep));
			assertEquals(BidResult.REJECTED_NOTMULTIPLE, bidResult);
			assertEquals("Auto bid should not be placed", oldWinningBid, openAuction.getWinningBid().getPrice());
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	@Test
	public void testSecondProxyEqualToFirst() {
		BidResult bidResult;
		try {
			// first proxy
			Money proxyBidPrice = new Money("550");
			openAuction.placeBid(new ProxyBid(proxyBidPrice, new DateTime(), zubin));
			
			// second proxy
			LOGGER.info("second proxy is equal to first proxy");
			Money secondProxyBidPrice = new Money("550");
			bidResult = openAuction.placeBid(new ProxyBid(secondProxyBidPrice, new DateTime(), deep));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Auto bid should be placed for first bidder", secondProxyBidPrice, openAuction.getWinningBid().getPrice());
			assertEquals(zubin, openAuction.getWinner());
			
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	/**
	 * <strong>Scenario:</strong>
	 * <ol>
	 * <li>Bidder 1 places proxy</li>
	 * <li>Bidder 2 places higher proxy</li>
	 * </ol>
	 * <strong>Expected Result:</strong> bid should be placed with next bid price of Bidder 1's proxy
	 * in favour of Bidder1. Another bid should be placed for Bidder 2 with next bid price.
	 */
	@Test
	public void testSecondProxyHigherThanFirst1() {
		BidResult bidResult;
		try {
			// first proxy
			Money proxyBidPrice = new Money("550");
			openAuction.placeBid(new ProxyBid(proxyBidPrice, new DateTime(), zubin));
			
			// second proxy
			bidResult = openAuction.placeBid(new ProxyBid(new Money("1000"), new DateTime(), deep));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Auto bid should be placed with price next increment of previous proxy", 
					proxyBidPrice.plus(openAuction.getDeltaPrice()), openAuction.getWinningBid().getPrice());
			assertEquals("Auto bid should be placed on behalf of new proxy bidder", deep, openAuction.getWinner());
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
	
	/**
	 * <strong>Scenario:</strong>
	 * <ol>
	 * <li>Bidder 1 places proxy</li>
	 * <li>Lead price exceeds that proxy</li>
	 * <li>Bidder 2 places higher proxy (above lead price)</li>
	 * </ol>
	 * <strong>Expected Result:</strong> Bid will be placed for Bidder 2 with next bid price.
	 * 
	 */
	@Test
	public void testSecondProxyHigherThanFirst2() {
		BidResult bidResult;
		try {
			Money deltaPrice = openAuction.getDeltaPrice();

			// first proxy
			openAuction.placeBid(new ProxyBid(new Money("550"), new DateTime(), zubin));
			
			openAuction.placeBid(new Bid(new Money("1000"), new DateTime(), deep));
			Money leadPrice = openAuction.getWinningBid().getPrice();
			
			// second proxy
			bidResult = openAuction.placeBid(new ProxyBid(new Money("1200"), new DateTime(), deep));
			assertEquals(BidResult.ACCEPTED, bidResult);
			assertEquals("Auto bid should be placed for Bidder 2", leadPrice.plus(deltaPrice), openAuction.getWinningBid().getPrice());
			assertEquals("Auto bid should be placed on behalf of new proxy bidder", deep, openAuction.getWinner());
		} catch (NotAttachedException e) {
			LOGGER.warn("Error in test case");
			fail("Bidder not attached!");
		}
	}
}
