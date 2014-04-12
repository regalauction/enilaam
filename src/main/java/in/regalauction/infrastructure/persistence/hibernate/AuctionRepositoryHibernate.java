package in.regalauction.infrastructure.persistence.hibernate;

import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.AuctionCode;
import in.regalauction.domain.model.auction.AuctionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;


@Repository
public class AuctionRepositoryHibernate extends HibernateRepository implements
		AuctionRepository {

	@Override
	public Auction findByAuctionCode(final AuctionCode auctionCode) {
		return (Auction) getSession().createQuery("from Auction where auctionCode = :auctionCode")
				.setParameter("auctionCode", auctionCode)
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Auction> findUpcommingAuctions() {
		List<Auction> returnList = getSession().createQuery("from Auction auction " +
				"left join fetch auction.documents " +
				"left join fetch auction.item item " +
				"left join fetch item.thumbnail " +
				"where startDate > current_timestamp() + hour(3) + minute(30)").list();
		return new HashSet<Auction>(returnList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Auction> findOldAuctions(final String username) {
		List<Auction> returnList = getSession().createQuery("select auction from Auction auction " +
				"join auction.users user where auction.endDate < current_timestamp() + hour(3) + minute(30) " +
				"and user.username = :username")
				.setString("username", username)
				.list();
		return new HashSet<Auction>(returnList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Auction> findAll() {
		return getSession().createCriteria(Auction.class).list();
	}

	@Override
	public AuctionCode nextAuctionCode() {
		// TODO replace with a DB sequence
		String string = UUID.randomUUID().toString();
		return new AuctionCode(string.substring(0, string.indexOf("-")));
	}

	@Override
	public void store(Auction auction) {
		getSession().saveOrUpdate(auction);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Auction> findRunningAuctions(final String username) {
		List<Auction> resultList = getSession().createQuery("select auction from Auction auction " +
				"left join fetch auction.bids " + 
				"left join fetch auction.documents " +
				"left join fetch auction.item item " +
				"left join fetch item.thumbnail " +
				"join auction.users user where auction.startDate < current_timestamp() + hour(3) + minute(30) and auction.endDate > current_timestamp() + hour(3) + minute(30) " +
				"and user.username = :username")
				.setString("username", username)
				.list();
		return new HashSet<Auction>(resultList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Auction> findRunningAuctions() {
		List<Auction> resultList = getSession().createQuery("select auction from Auction auction " +
				"left join fetch auction.documents " +
				"left join fetch auction.item item " +
				"left join fetch item.thumbnail " +
				"join auction.bids bid " +
				"join auction.users user where auction.startDate < current_timestamp() + hour(3) + minute(30) and auction.endDate > current_timestamp() + hour(3) + minute(30) " +
				"order by bid.bidTime desc")
				.list();
		return new HashSet<Auction>(resultList);
	}
}
