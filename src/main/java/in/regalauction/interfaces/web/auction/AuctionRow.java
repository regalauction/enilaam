package in.regalauction.interfaces.web.auction;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AuctionRow {
	
	@NotNull
	private String auctionCode;
	@NotNull
	@Min(0)
	private BigDecimal bidPrice;
	private boolean proxy;
	
	public String getAuctionCode() {
		return auctionCode;
	}

	public void setAuctionCode(String auctionCode) {
		this.auctionCode = auctionCode;
	}

	public BigDecimal getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(BigDecimal bidPrice) {
		this.bidPrice = bidPrice;
	}

	public boolean isProxy() {
		return proxy;
	}

	public void setProxy(boolean proxy) {
		this.proxy = proxy;
	}

}