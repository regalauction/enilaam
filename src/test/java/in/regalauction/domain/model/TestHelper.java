package in.regalauction.domain.model;

import org.joda.time.DateTime;

import in.regalauction.domain.model.auction.AuctionCode;
import in.regalauction.domain.model.auction.DutchAuction;
import in.regalauction.domain.model.auction.EnglishAuction;
import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.types.Money;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserTest;


public class TestHelper {
	
	private TestHelper() {}
	
	/**
	 * @param username
	 * @return dummy user with bidder authority
	 */
	public static User dummyBidder(final String username) {
		return UserTest.dummyBidder(username);
	}
	
	/**
	 * @param name
	 * @return dummy name
	 */
	public static Item dummyItem(final String name) {
		return new Item(name, name);
	}
	
	/**
	 * @return A dummy EnglishAuction without any users attached
	 */
	public static EnglishAuction dummyEnglishAuction() {
		return  new EnglishAuction(new AuctionCode("ABC"), "english", dummyItem("i1"), 
				new Money("100"), new Money("10000"), new Money("10"), 
				(new DateTime()).minusMinutes(2), (new DateTime()).plusDays(1), 2);
	}

	/**
	 * @return A dummy DutchAuction without any users attached
	 */
	public static DutchAuction dummyDutchAuction() {
		return  new DutchAuction(new AuctionCode("ABC"), "dutch", dummyItem("i1"), 
				new Money("10000"), new Money("100"), new Money("10"), 
				(new DateTime()).minusMinutes(2), (new DateTime()).plusDays(1), 2);
	}

}
