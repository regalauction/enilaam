package in.regalauction.interfaces.web.auction;

import in.regalauction.domain.model.attachment.Document;
import in.regalauction.domain.model.user.User;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;



public class AuctionForm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuctionForm.class);
	
	@NotBlank private String name;
	
	@NotNull
	@DateTimeFormat(style = "LS") 
	private DateTime startDate;
	
	@NotNull
	@DateTimeFormat(style = "LS")
	private DateTime endDate;
	
	@NotBlank private String item;
	
	@NotNull 
	@Min(1)
	private Float quantity;
	
	@NotNull
	@Min(0)
	@Digits(integer = 8, fraction = 2) 
	private BigDecimal basePrice;
	
	@NotNull
	@Min(0)
	@Digits(integer = 8, fraction = 2)
	private BigDecimal reservePrice;
	
	@NotNull
	@Min(0)
	@Digits(integer = 8, fraction = 2)
	private BigDecimal deltaPrice;

	@NotNull
	@Min(0)	
	private Integer timeExtension;
	private AuctionType auctionType;
	private String[] bidders;
	
	private Set<Document> currDocuments;
	private String[] addFiles;
	private String[] deleteFiles;
	
	public AuctionForm() {
		int minuteOfHour = DateTime.now().getMinuteOfHour();
		startDate = DateTime.now().plusMinutes(60 - minuteOfHour);
		endDate = startDate.plusHours(1);
		timeExtension = 0;
		currDocuments = new HashSet<Document>();
		quantity = 1f;
	}
	
	public AuctionForm(final AuctionViewAdapter auctionViewAdapter) {
		name = auctionViewAdapter.getName();
		startDate = auctionViewAdapter.getStartDate();
		endDate = auctionViewAdapter.getEndDate();
		item = auctionViewAdapter.getItem().getCode();
		quantity = auctionViewAdapter.getQuantity();
		
		Set<User> attachedBidders = auctionViewAdapter.getBidders();
		LOGGER.debug("Attached Bidders: {}", attachedBidders.size());
		String[] strings = new String[attachedBidders.size()];
		int i = 0;
		for (User user : attachedBidders) {
			strings[i++] = user.getUsername();
		}
		bidders = strings;
		
		currDocuments = auctionViewAdapter.getDocuments();
		
		LOGGER.debug("set users: {}", bidders);
		
		auctionType = auctionViewAdapter.getAuctionType();
		basePrice = auctionViewAdapter.getBasePrice().getAmount();
		reservePrice = auctionViewAdapter.getReservePrice().getAmount();
		deltaPrice = auctionViewAdapter.getDeltaPrice().getAmount();
		timeExtension = auctionViewAdapter.getTimeExtension();
		
	}
	

	public String[] getBidders() {
		return bidders;
	}

	public void setBidders(String[] bidders) {
		this.bidders = bidders;
	}

	public AuctionType getAuctionType() {
		return auctionType;
	}
	

	public void setAuctionType(AuctionType auctionType) {
		this.auctionType = auctionType;
	}



	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public DateTime getStartDate() {
		return startDate;
	}


	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}


	public DateTime getEndDate() {
		return endDate;
	}


	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public String getItem() {
		return item;
	}
	
	public Float getQuantity() {
		return quantity;
	}

	public void setItem(String code) {
		this.item = code;
	}
	
	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}


	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}


	public BigDecimal getReservePrice() {
		return reservePrice;
	}


	public void setReservePrice(BigDecimal reservePrice) {
		this.reservePrice = reservePrice;
	}


	public BigDecimal getDeltaPrice() {
		return deltaPrice;
	}


	public void setDeltaPrice(BigDecimal deltaPrice) {
		this.deltaPrice = deltaPrice;
	}


	public Integer getTimeExtension() {
		return timeExtension;
	}


	public void setTimeExtension(Integer timeExtension) {
		this.timeExtension = timeExtension;
	}

	public Set<Document> getCurrDocuments() {
		return currDocuments;
	}

	public String[] getAddFiles() {
		return addFiles;
	}

	public void setAddFiles(String[] addFiles) {
		this.addFiles = addFiles;
	}

	public String[] getDeleteFiles() {
		return deleteFiles;
	}

	public void setDeleteFiles(String[] deleteFiles) {
		this.deleteFiles = deleteFiles;
	}


}
