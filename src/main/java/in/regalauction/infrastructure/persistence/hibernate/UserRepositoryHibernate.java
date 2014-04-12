package in.regalauction.infrastructure.persistence.hibernate;

import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserRepository;

import java.util.Collection;

import org.springframework.stereotype.Repository;


/**
 * Hibernate implementation of UserRepository
 * @author Diptangshu Chakrabarty
 *
 */
@Repository
public class UserRepositoryHibernate extends HibernateRepository implements
		UserRepository {
	
	@Override
	public User findByUsername(String username) {
		return (User) getSession().createQuery("from User where username = :username")
				.setParameter("username", username)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<User> findAll() {
		return getSession().createCriteria(User.class).list();
	}

	@Override
	public void store(User user) {
		getSession().saveOrUpdate(user);
		
	}

}
