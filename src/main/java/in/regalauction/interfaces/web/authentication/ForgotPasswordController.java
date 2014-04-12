package in.regalauction.interfaces.web.authentication;

import in.regalauction.application.UserService;
import in.regalauction.infrastructure.security.InvalidResetPasswordException;
import in.regalauction.interfaces.web.user.ForgotPassword;
import in.regalauction.interfaces.web.user.ResetPassword;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ForgotPassword view() {
		LOGGER.trace("view()");
		return new ForgotPassword();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String mailResetPasswordLink(@Valid ForgotPassword forgotPassword, 
			BindingResult result, Model model, HttpServletRequest request) {
		
		LOGGER.debug("User requested to reset password from Forgot Password.");
		
		if (result.hasErrors())
			return "forgotPassword";
		
		String username = forgotPassword.getUsername();
		
		String linkBaseUrl = request.getRequestURL().toString().concat("/reset/");

		try {
			userService.sendResetPasswordMail(username, linkBaseUrl);
		} catch (UsernameNotFoundException e) {
			return "forgotPassword";
		}
		
		model.addAttribute("email", username);
		return "forgotPassword/mailSent";
	}
	
	@RequestMapping(value = "/reset/{code}", method = RequestMethod.GET)
	public ResetPassword getResetPasswordForm(@PathVariable("code") String code, Model model) 
			throws InvalidResetPasswordException {
		LOGGER.trace("Reset password form");
		
		model.addAttribute("username", userService.findUsernameForResetPassword(code));
		
		return new ResetPassword(code);
	}
	
	@RequestMapping(value = "/reset/{code}", method = RequestMethod.POST)
	public String resetPassword(@PathVariable("code") String code, @Valid ResetPassword resetPassword, BindingResult result) 
			throws InvalidResetPasswordException {
		
		String errorView = new StringBuilder("forgotPassword/reset/").append(code).toString();
		
		if (result.hasErrors())
			return errorView;
		
		if (!resetPassword.getNewPassword().equals(resetPassword.getRetypePassword())) {
			result.rejectValue("retypePassword", "Mismatch.changePassword.retypePassword");
			return errorView;
		}
		
		userService.resetPassword(code, resetPassword.getNewPassword());
		
		return "redirect:/home";
	}
}
