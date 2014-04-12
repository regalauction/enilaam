package in.regalauction.application;

import in.regalauction.domain.model.user.DuplicateUserException;
import in.regalauction.domain.model.user.User;
import in.regalauction.infrastructure.security.InvalidResetPasswordException;
import in.regalauction.interfaces.web.user.UserForm;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;


public interface UserService extends UserDetailsManager {
	
	String getCurrentUsername();
	
	User getCurrentUser();

	List<String> findAllGroups();

	void createUser(UserForm userForm) throws DuplicateUserException;

	void updateUser(UserForm userForm);
	
	String findGroupByUsername(String username);

	User findByUsername(String username);
	
	boolean isEnabled(String username);

	Collection<User> findBidders();
	
	Collection<User> findObservers();
	
	void sendResetPasswordMail(String username, String linkBaseUrl) throws UsernameNotFoundException;

	String findUsernameForResetPassword(String code) throws InvalidResetPasswordException;

	void resetPassword(String code, String newPassword) throws InvalidResetPasswordException;

}
