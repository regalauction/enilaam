package in.regalauction.domain.model.auction;

import in.regalauction.domain.model.attachment.Document;
import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.shared.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class consists of the activities that any Auction should have. This is the root of the 
 * auction heirarchy.
 * 
 * <p>Auction is an abstract class. This should be extended to implement the functionalities
 * specific to a particular auction logic, such as determining the winning bid.
 * @author Diptangshu Chakrabarty
 * @version 1
 * @since 1
 *
 */
public abstract class Auction implements Entity<Auction> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Auction.class);

	protected AuctionCode auctionCode;
	protected Item item;
	protected String name;
	protected Set<Document> documents = Collections.emptySet();
	
	protected DateTime startDate;
	protected DateTime endDate;

	/**
	 * Users attached to the auction.
	 */
	protected Set<User> users = new HashSet<User>();
	
	/**
	 * Holds all the accepted bids in the order of submission.
	 */
	protected List<Bid> bids = new ArrayList<Bid>();
	
	protected Auction(final AuctionCode auctionCode, final Item item, final String name, final DateTime startDate, final DateTime endDate) {
		super();
		
		Validate.notNull(auctionCode);
		Validate.notEmpty(name);
		Validate.notNull(item);
		Validate.notNull(startDate);
		Validate.notNull(endDate);
		Validate.isTrue(endDate.isAfter(startDate));
		
		this.auctionCode = auctionCode;
		this.item = item;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		
	}
	
	public Item getItem() {
		return item;
	}
	
	public void addUser(final User user) {
		
		Validate.notNull(user);
		users.add(user);
	}
	
	public void setUsers(final Set<User> users) {
		Validate.notEmpty(users);
		this.users = users;
	}
	
	public boolean isAttached(final User user) {
		LOGGER.trace("Checking whether user {} is attached to {}.", user, this);
		return users.contains(user);
	}

	/**
	 * Auction is running or not.
	 * @return true if auction has ended.
	 */
	public boolean isRunning() {
		return startDate.isBeforeNow() && endDate.isAfterNow();
	}
	
	/**
	 * This method checks whether any valid bid has been submitted in the auction or not.
	 * @return <code>true</code> if no bid has been accepted in the auction
	 */
	public boolean hasWinningBid() {
		return !bids.isEmpty();
	}
	
	/**
	 * The current winning bid of the auction. If called during auction then it returns the bid
	 * that is currently winning. If called after auction has ended, then it returns the bid that
	 * has won.
	 * @return currently winning bid
	 */
	public abstract Bid getWinningBid();
	
	/**
	 * The current winner of the auction. Uses {@link Auction#getWinningBid()} to find the winner.
	 * @return Bidder who has won the auction.
	 * @see Auction#getWinningBid()
	 */
	public User getWinner() {
		return getWinningBid().getBidder();
	}
	
	public BidResult placeBid(final Bid bid) throws NotAttachedException {
		
		LOGGER.info("Bid placed on {} - {}", this, bid);
		
		Validate.notNull(bid);
		Validate.notEmpty(users);
		
		if (!isAttached(bid.getBidder()))
			throw new NotAttachedException();
		
		BidResult bidResult = addBid(bid);
		LOGGER.info("{} was {}", bid, bidResult.toString());
		
		return bidResult;
	}
	
	/**
	 * This method is called to add a bid to the auction. Returns a {@link BidResult} object
	 * that indicates whether the bid was accepted or not.
	 * @param bid the bid to be submitted
	 * @return bid result indicated whether the bid was accepted or not.
	 */
	protected abstract BidResult addBid(Bid bid);
	
	
	@Override
	public boolean sameIdentityAs(Auction other) {
		
		return other != null && this.auctionCode.equals(other.auctionCode);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		final Auction other = (Auction) obj;
		return sameIdentityAs(other);
	}
	
	@Override
	public int hashCode() {
		return this.auctionCode.hashCode();
	}

	/**
	 * Add document to the list of documents attached to the auction.
	 * @param document the new document
	 */
	public void addDocument(final Document document) {
		
		Validate.notNull(document);
		
		LOGGER.trace("Added document {} to auction {}", document, this);
		
		if (documents.isEmpty())
			documents = new HashSet<Document>();
		
		documents.add(document);
	}
	
	public Set<Document> getDocuments() {
		return Collections.unmodifiableSet(documents);
	}
	
	public void removeDocument(final String code) {
		for (Iterator<Document> iterator = documents.iterator(); iterator.hasNext();) {
			Document document = (Document) iterator.next();
			if (document.getCode().equals(code)) {
				iterator.remove();
			}
		}
	}

	public AuctionCode getAuctionCode() {
		return auctionCode;
	}

	public String getName() {
		return name;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	
	public int getBidCount() {
		return bids.size();
	}

	@Override
	public String toString() {
		return new StringBuilder()
			.append("[").append(auctionCode).append("] ")
			.append(name).toString();
	}


	// Auto-generated surrogate key
	Long id;
	
	Auction() {
		// Needed by Hibernate
	}
	

}
