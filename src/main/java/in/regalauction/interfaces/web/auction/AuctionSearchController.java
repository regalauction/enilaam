package in.regalauction.interfaces.web.auction;

import in.regalauction.application.UserService;
import in.regalauction.domain.model.auction.Auction;
import in.regalauction.domain.model.auction.AuctionRepository;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;
import in.regalauction.interfaces.bidding.facade.BiddingFacade;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auction")
@Transactional(readOnly = true)
public class AuctionSearchController {
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	@Autowired
	private BiddingFacade biddingFacade;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public @ModelAttribute("auctions") Collection<Auction> list() {
		return auctionRepository.findAll();
	}
	
	@RequestMapping("/upcoming")
	public @ModelAttribute("auctions") Collection<AuctionViewAdapter> getUpcomingAuctions() {
		return biddingFacade.fetchUpcomingAuctions();
	}
	
	@RequestMapping("/history")
	public @ModelAttribute("auctions") Collection<AuctionViewAdapter> getAuctionHistory() {
		return biddingFacade.fetchOldAuctions(userService.getCurrentUsername());
	}
	
	@RequestMapping("/running")
	public @ModelAttribute("auctions") List<AuctionViewAdapter> getRunningAuctions() {
		return AuctionViewAdapter.toList(auctionRepository.findRunningAuctions());
	}
	
}
