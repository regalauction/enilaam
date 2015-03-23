package in.regalauction.interfaces.web.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

	@RequestMapping("/winners")
	public void winners() {}
	
	@RequestMapping(value = "/bidDetails", method = RequestMethod.GET)
	public void bidDetailsSearch() {}
	
	@RequestMapping(value = "/bidDetails", method = RequestMethod.POST)
	public void bidDetails(@RequestParam("auctionCode") String auctionCode, Model model) { 
		LOGGER.trace("auctionCode: {}", auctionCode);
		model.addAttribute("auctionCode", auctionCode); 
	}
	
	@RequestMapping(value = "/biddersByRank", method = RequestMethod.GET)
	public void biddersByRankSearch() {}
	
	@RequestMapping(value = "/biddersByRank", method = RequestMethod.POST)
	public void biddersByRank(@RequestParam("auctionCode") String auctionCode, Model model) { 
		LOGGER.trace("auctionCode: {}", auctionCode);
		model.addAttribute("auctionCode", auctionCode); 
	}
}
