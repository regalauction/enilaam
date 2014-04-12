package in.regalauction.domain.model.auction;

import java.util.Collection;

public interface AuctionRepository {

	Auction findByAuctionCode(AuctionCode auctionCode);
	
	Collection<Auction> findUpcommingAuctions();
	
	Collection<Auction> findOldAuctions(String username);
	
	Collection<Auction> findRunningAuctions();
	
	Collection<Auction> findRunningAuctions(String username);
	
	Collection<Auction> findAll();
	
	AuctionCode nextAuctionCode();
	
	void store(Auction auction);


}
