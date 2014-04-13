package in.regalauction.infrastructure.persistence.hibernate;

import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.AuctionCode;
import in.regalauction.domain.model.auction.AuctionRepository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

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
		// Use the auto increment value of auction_id as the next code
		BigInteger nextId = (BigInteger) getSession().createSQLQuery("select auto_increment from information_schema.tables where table_name = 'auction' and table_schema = DATABASE()").uniqueResult();
		return new AuctionCode(nextId.toString());
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
				"and user.username = :username " +
				"order by auction.auctionCode")
				.setString("username", username)
				.list();
		return new LinkedHashSet<Auction>(resultList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Auction> findRunningAuctions() {
		List<Auction> resultList = getSession().createQuery("select auction from Auction auction " +
				"left join fetch auction.documents " +
				"left join fetch auction.item item " +
				"left join fetch item.thumbnail " +
				"left join auction.bids bid " +
				"join auction.users user where auction.startDate < current_timestamp() + hour(3) + minute(30) and auction.endDate > current_timestamp() + hour(3) + minute(30) " +
				"order by bid.bidTime desc, auction.auctionCode")
				.list();
		return new LinkedHashSet<Auction>(resultList);
	}
}
