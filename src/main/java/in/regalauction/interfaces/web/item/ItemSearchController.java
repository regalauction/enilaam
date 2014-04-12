package in.regalauction.interfaces.web.item;

import in.regalauction.domain.model.item.Item;
import in.regalauction.domain.model.item.ItemRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/item")
@Transactional(readOnly = true)
public class ItemSearchController {

	@Autowired
	private ItemRepository itemRepository;
	
	@RequestMapping
	public @ModelAttribute("items") Collection<Item> list() {
		return itemRepository.findAll();
	}
	
}
 