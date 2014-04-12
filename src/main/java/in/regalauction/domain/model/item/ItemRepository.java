package in.regalauction.domain.model.item;

import java.util.Collection;

public interface ItemRepository {
	
	void store(Item item);

	Collection<Item> findAll();
	
	Item findByCode(String code);
}
