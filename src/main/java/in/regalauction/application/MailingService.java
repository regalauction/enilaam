package in.regalauction.application;

import in.regalauction.domain.model.spotdeal.SpotDealItem;
import in.regalauction.domain.model.user.User;

public interface MailingService {

	void sendWelcomeMail(String to, String firstName, String password);

	void sendResetPasswordMail(String to, String resetPasswordLink);

	void sendSpotDealOrderMail(SpotDealItem spotDealItem, User bidder, Long quantity);

	void sendRegistrationRequestMail(String userMail, String data);
}
