package in.regalauction.application;

public interface MailingService {

	void sendWelcomeMail(String to, String firstName, String password);

	void sendResetPasswordMail(String to, String resetPasswordLink);
}
