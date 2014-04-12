package in.regalauction.infrastructure.persistence.hibernate;

import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.item.ItemRepository;

import java.util.Collection;

import org.springframework.stereotype.Repository;


@Repository
public class ItemRepositoryHibernate extends HibernateRepository implements
		ItemRepository {
	
	@Override
	public void store(final Item item) {
		getSession().saveOrUpdate(item);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Item> findAll() {
		return getSession().createCriteria(Item.class).list();
	}

	@Override
	public Item findByCode(final String code) {
		return (Item) getSession().createQuery("from Item where code = :code")
				.setParameter("code", code)
				.uniqueResult();
		
	}

}
