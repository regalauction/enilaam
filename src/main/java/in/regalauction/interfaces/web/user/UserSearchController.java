package in.regalauction.interfaces.web.user;

import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
@Transactional(readOnly = true)
public class UserSearchController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ModelAttribute("users") Collection<User> list() {
		return userRepository.findAll();
	}
}
 