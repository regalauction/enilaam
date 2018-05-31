package in.regalauction.interfaces.web.authentication;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public RegistrationForm getForm() {
		LOGGER.debug("Getting new registration form");
		return new RegistrationForm();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid RegistrationForm registrationForm, BindingResult result) {
		LOGGER.debug("Accepting registration request");
		
		if (result.hasErrors()) return "register";
		
		LOGGER.debug("Registration request accepted.");
		return "register/success";
	}

}
