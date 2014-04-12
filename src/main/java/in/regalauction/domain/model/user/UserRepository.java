package in.regalauction.domain.model.user;

import java.util.Collection;

/**
 * 
 * @author Diptangshu Chakrabarty
 * @version 1
 *
 */
public interface UserRepository {

	Collection<User> findAll();
	
	User findByUsername(String username);

	void store(User user);
	
}
