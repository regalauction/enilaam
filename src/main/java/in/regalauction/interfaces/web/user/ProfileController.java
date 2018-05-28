package in.regalauction.interfaces.web.user;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import in.regalauction.application.UserService;
import in.regalauction.domain.model.user.User;


@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public User populateUser() {
		return userService.getCurrentUser();
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ChangePassword view() {
		LOGGER.trace("view()");
		return new ChangePassword();
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@Valid ChangePassword changePassword, BindingResult result) {
		LOGGER.trace("Changing password");
		if (result.hasErrors())
			return "profile/changePassword";
		
		if (!changePassword.getNewPassword().equals(changePassword.getRetypePassword())) {
			result.rejectValue("retypePassword", "Mismatch.changePassword.retypePassword");
			return "profile/changePassword";
		}
		
		try {
			userService.changePassword(changePassword.getOldPassword(), changePassword.getNewPassword());
		} catch (AuthenticationException e) {
			result.rejectValue("oldPassword", "Invalid.changePassword.oldPassword");
			return "profile/changePassword";
		}
		
		
		SecurityContextHolder.clearContext();
		return "profile/changePasswordSuccess";
	}
}
