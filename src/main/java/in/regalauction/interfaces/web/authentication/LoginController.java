package in.regalauction.interfaces.web.authentication;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import in.regalauction.application.UserService;
import in.regalauction.domain.model.auction.AuctionRepository;
import in.regalauction.domain.model.spotdeal.SpotDealItemRepository;
import in.regalauction.interfaces.bidding.AuctionViewAdapter;
import in.regalauction.interfaces.bidding.facade.BiddingFacade;


@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private BiddingFacade biddingFacade;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SpotDealItemRepository spotDealItemRepository;
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	@RequestMapping("/login")
	@Transactional(readOnly = true)
	public void login(ModelMap map) throws Exception {
		LOGGER.trace("Login handler called.");
		map.addAttribute("auctions", biddingFacade.fetchUpcomingAuctions());
		map.addAttribute("spotDeals", spotDealItemRepository.fetchActiveItems());
	}
	
	@RequestMapping("/home")
	@Transactional(readOnly = true)
	public String home(ModelMap map) {
		LOGGER.trace("Fetching data for home page");
		
		String currentUsername = userService.getCurrentUsername();
		String group = userService.findGroupByUsername(currentUsername);
		LOGGER.debug("{} is a {}", currentUsername, group);

		map.addAttribute("numRunningAuctions", biddingFacade.fetchRunningAuctions(currentUsername).size());
		map.addAttribute("hasAuctionHistory", !biddingFacade.fetchOldAuctions(currentUsername).isEmpty());
		map.addAttribute("hasUpcommingAuctions", !auctionRepository.findUpcommingAuctions().isEmpty());
		
		map.addAttribute("spotDeals", spotDealItemRepository.fetchActiveItems());
		
		// Return a view name specific to the user group
		return new StringBuilder("home").append("_").append(group).toString();
		
	}
	
	@RequestMapping("/about")
	public void about() {
		LOGGER.trace("about");
	}
	
	@RequestMapping("/services")
	public void services() {
		LOGGER.trace("services");
	}
	
}
