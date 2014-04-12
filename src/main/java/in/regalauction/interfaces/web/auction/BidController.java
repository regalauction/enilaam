package in.regalauction.interfaces.web.auction;

import in.regalauction.application.UserService;
import in.regalauction.domain.model.auction.BidResult;
import in.regalauction.domain.model.types.Money;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;
import in.regalauction.interfaces.bidding.BidResultViewAdapter;
import in.regalauction.interfaces.bidding.facade.BiddingFacade;

import java.util.Collection;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/auction")
@Transactional(readOnly = true)
public class BidController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BidController.class);
	
	@Autowired
	private BiddingFacade biddingFacade;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/bid", method = RequestMethod.GET)
	public @ModelAttribute("auctions") Collection<AuctionViewAdapter> getBids() {
		LOGGER.trace("getBids()");
		return biddingFacade.fetchRunningAuctions(userService.getCurrentUsername());
	}
	
	
	@RequestMapping(value = "/bid", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String placeBids(@Valid AuctionRow auctionRow, BindingResult result, ModelMap map) throws Exception {

		
		String username = userService.getCurrentUsername();

		LOGGER.debug("Binding errors? {}", result.hasErrors());
		if (result.hasErrors()) {
			
			if (!result.hasFieldErrors("auctionCode")) {
				AuctionViewAdapter auction = biddingFacade.fetchAuction(username, auctionRow.getAuctionCode());
				map.addAttribute("auction", auction);
			}
			
			return "auction/refresh";
			
		}
		
		
		String auctionCode = auctionRow.getAuctionCode();
		Money bidPrice = new Money(auctionRow.getBidPrice());
		DateTime bidTime = new DateTime();
		boolean proxy = auctionRow.isProxy();
		
		String logMessage = new StringBuilder()
				.append(" username: ").append(username)
				.append(" auctionCode: ").append(auctionCode)
				.append(" bidPrice: ").append(bidPrice)
				.append(" bidTime: ").append(bidTime)
				.append(" proxy: ").append(proxy)
				.toString();
		LOGGER.debug(logMessage);
		
		BidResult bidResult = biddingFacade.placeBid(username, auctionCode, bidPrice, bidTime, proxy);
		
		AuctionViewAdapter auction = biddingFacade.fetchAuction(username, auctionCode);
		
		map.addAttribute("auction", auction);
		map.addAttribute("bidResult", new BidResultViewAdapter(bidResult));
		
		return "auction/refresh";
		
	}
	
	@RequestMapping(value = "/{auctionCode}/images")
	public String getImages(@PathVariable("auctionCode") String auctionCode, ModelMap map) throws Exception {
		LOGGER.trace("Fetching images for auction: {}", auctionCode);
		
		String username = userService.getCurrentUsername();
		
		AuctionViewAdapter auctionViewAdapter = biddingFacade.fetchAuction(username, auctionCode);
		
		map.put("images", auctionViewAdapter.getImages());
		
		// TODO Tiles mapping not working for this class! need to investigate
//		return new StringBuilder("auction/").append(auctionCode).append("/images").toString();
		return "auction/images";
	}
}
