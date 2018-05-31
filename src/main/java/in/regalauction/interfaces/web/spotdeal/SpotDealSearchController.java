package in.regalauction.interfaces.web.spotdeal;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import in.regalauction.domain.model.spotdeal.SpotDealItem;
import in.regalauction.domain.model.spotdeal.SpotDealItemRepository;


@Controller
@RequestMapping("/spotdeals")
@Transactional(readOnly = true)
public class SpotDealSearchController {

	@Autowired
	private SpotDealItemRepository spotDealItemRepository;
	
	@RequestMapping
	public @ModelAttribute("spotdeals") Collection<SpotDealItem> list() {
		return spotDealItemRepository.findAll();
	}
	
}
 