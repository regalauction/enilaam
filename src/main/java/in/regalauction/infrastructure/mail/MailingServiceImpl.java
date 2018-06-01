package in.regalauction.infrastructure.mail;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import in.regalauction.application.MailingService;
import in.regalauction.domain.model.spotdeal.SpotDealItem;
import in.regalauction.domain.model.user.User;


public class MailingServiceImpl implements MailingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MailingServiceImpl.class);
	
	private MailSender mailSender;
	private SimpleMailMessage messageTemplate;
	private boolean disabled;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMessageTemplate(SimpleMailMessage messageTemplate) {
		this.messageTemplate = messageTemplate;
	}
	
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	private void sendMail(final String to, final String subject, final String body) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage(messageTemplate);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		
		LOGGER.debug(mailMessage.toString());
		
		if (!disabled) {
			try {
				mailSender.send(mailMessage);
				LOGGER.info("Mail sent to {}", to);
				LOGGER.trace(body);
			} catch (MailException e) {
				LOGGER.warn("Could not send mail to {}", to);
				e.printStackTrace();
			}
		} else LOGGER.info("Mail sending is disabled!");
	}
	
	@Override
	public void sendWelcomeMail(final String to, final String firstName, final String password) {
		LOGGER.info("Sending welcomce mail to {}", to);
		
		Validate.notEmpty(to);
		Validate.notEmpty(password);
		
		StringBuilder body = new StringBuilder("Hello");
		
		if (!firstName.isEmpty()) body.append(" ").append(firstName);
		
		body.append("!\n\n")
		.append("Your account has been created.\n")
		.append("Your current password is: ").append(password).append("\n")
		.append("Please change your password after you log in.\n\n")
		.append("Thank you\n");
		
		sendMail(to, "Welcome to RegalAuction!", body.toString());
	}

	@Override
	public void sendResetPasswordMail(final String to, final String resetPasswordLink) {
		LOGGER.info("Sending reset password mail to {}", to);
		
		Validate.notEmpty(to);
		Validate.notEmpty(resetPasswordLink);
		
		StringBuilder body = new StringBuilder("Hello!\n\n")
			.append("Please use the following link to reset your password.\n\n")
			.append(resetPasswordLink).append("\n\n")
			.append("Thank You\n");
		
		sendMail(to, "Your reset password link", body.toString());
		
	}
	
	@Override
	public void sendSpotDealOrderMail(final SpotDealItem spotDealItem, final User bidder, final Long quantity) {
		
		LOGGER.info("Sending spot deal order mail");
		
		Validate.notNull(spotDealItem);
		Validate.isTrue(quantity > 0);
		
		StringBuilder subject = new StringBuilder("Spot Deal Purchased: ")
				.append(spotDealItem.getCode());		
		
		StringBuilder bodyFrag = new StringBuilder();
		
		bodyFrag.append("Hello!\n\n").append("The following spot deal order was placed\n\n")
		.append("Name: ").append(spotDealItem.getName())
		.append("\n")
		.append("Seller: ").append(spotDealItem.getSellerName())
		.append("\n")
		.append("Quantity ordered: ").append(quantity);
		
		String footer = "\n\nThanks!\n";
		
		String bodyToBidder = bodyFrag.toString() + footer;
		
		sendMail(bidder.getUsername(), subject.toString(), bodyToBidder);		
		
		String bodyToClient = bodyFrag.append("\n")
				.append("Remaining quantity: ")
				.append(spotDealItem.getQuantity())
				.append(footer)
				.toString();
				
		sendMail(spotDealItem.getSellerEmail(), subject.toString(), bodyToClient);
	}

	@Override
	public void sendRegistrationRequestMail(String userMail, String data) {
		
		String subject = "User registration request submitted";

		StringBuilder body = new StringBuilder("Hello!\n\n")
				.append("A new user registration request was submitted with the following information:\n")
				.append(data)
				.append("\nPlease take a print out of this email and submit it to us with the supporting documents.")
				.append("\nThanks!");
		
		sendMail(userMail, subject, body.toString());
		
	}
	

}
