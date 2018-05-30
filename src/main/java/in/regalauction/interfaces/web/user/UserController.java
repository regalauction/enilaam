package in.regalauction.interfaces.web.user;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import in.regalauction.application.AddressFinderService;
import in.regalauction.application.UserService;
import in.regalauction.domain.model.user.DuplicateUserException;
import in.regalauction.domain.model.user.User;


@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private AddressFinderService addressFinderService;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void populateModel(Model model) {
		
		model.addAttribute("roles", userService.findAllGroups());
		List<String> states = addressFinderService.getStates();
		model.addAttribute("states", states);
		if (!states.isEmpty()) {
			model.addAttribute("cities", addressFinderService.getCities(states.get(0)));
		}
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("Manufacturer");
		list.add("Trader");
		
		model.addAttribute("userTypes", list);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public UserForm getEditForm(@RequestParam("q") String username, Model model) throws IllegalAccessException, InvocationTargetException {
		
		User user = userService.findByUsername(username);
		String groupName = userService.findGroupByUsername(username);
		boolean enabled = userService.isEnabled(username);
		
		if (!user.isStateEmpty())
			model.addAttribute("cities", addressFinderService.getCities(user.getAddress().getState()));
		
		return new UserForm(user, groupName, enabled);
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public UserForm getNewForm() {
		return new UserForm();
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String add(@Valid UserForm userForm, BindingResult result) {
		
		if (result.hasErrors())	return "user/new";
		
		try {
			userService.createUser(userForm);
		} catch (DuplicateUserException e) {
			result.rejectValue("username", "Duplicate.userForm.username");
			return "user/new";
		}
		
		String returnView = new StringBuilder("user/edit?q=").append(userForm.getUsername()).toString();
		return new StringBuilder("redirect:/").append(returnView).toString();
	}

	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String update(@RequestParam("q") String username, @Valid UserForm userForm, BindingResult result) {
		
		String returnView = new StringBuilder("user/edit?q=").append(username).toString();
		LOGGER.debug("return view: {}", returnView);
		
		if (result.hasErrors()) 
			return returnView;
		
		userService.updateUser(userForm);
		return new StringBuilder("redirect:/").append(returnView).toString();
	}
	
}
 