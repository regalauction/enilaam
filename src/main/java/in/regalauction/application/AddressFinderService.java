package in.regalauction.application;

import java.util.List;

public interface AddressFinderService {

	List<String> getStates();
	
	List<String> getCities(String state);
}
