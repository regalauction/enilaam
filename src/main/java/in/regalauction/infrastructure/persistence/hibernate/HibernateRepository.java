package in.regalauction.infrastructure.persistence.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Functionality common to all Hibernate repositories
 *
 */
public abstract class HibernateRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateRepository.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		
		LOGGER.trace("Is sessionFactory null? {}", sessionFactory==null);
		
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			LOGGER.warn(e.getMessage());
			throw e;
		}
	}
}
