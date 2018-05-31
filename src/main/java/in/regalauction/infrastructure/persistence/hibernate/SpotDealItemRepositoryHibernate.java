package in.regalauction.infrastructure.persistence.hibernate;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.spotdeal.SpotDealItem;
import in.regalauction.domain.model.spotdeal.SpotDealItemRepository;

public class SpotDealItemRepositoryHibernate extends HibernateRepository implements SpotDealItemRepository {

	@Override
	public void store(SpotDealItem spotDealItem) {
		getSession().saveOrUpdate(spotDealItem);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<SpotDealItem> findAll() {
		return getSession().createCriteria(SpotDealItem.class).list();
	}

	@Override
	public SpotDealItem findByCode(String code) {
		return (SpotDealItem) getSession().createQuery("from SpotDealItem where code = :code")
				.setParameter("code", code).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<SpotDealItem> fetchActiveItems() {
		List<SpotDealItem> returnList = getSession().createQuery("from SpotDealItem spotDealItem where quantity > 0 and enabled = true").list();
		return new HashSet<>(returnList);
	}

}
