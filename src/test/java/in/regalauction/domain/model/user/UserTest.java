package in.regalauction.domain.model.user;

import in.regalauction.domain.model.user.User;

public class UserTest {

	public static User dummyBidder(final String username) {
		return new User(username);
	}
}
