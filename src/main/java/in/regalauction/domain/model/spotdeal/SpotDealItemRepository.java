package in.regalauction.domain.model.spotdeal;

import java.util.Collection;

public interface SpotDealItemRepository {

	void store(SpotDealItem spotDealItem);

	Collection<SpotDealItem> findAll();

	SpotDealItem findByCode(String code);

	Collection<SpotDealItem> fetchActiveItems();
}
