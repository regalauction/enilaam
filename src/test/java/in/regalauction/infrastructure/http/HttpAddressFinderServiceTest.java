package in.regalauction.infrastructure.http;

import static org.junit.Assert.*;

import in.regalauction.infrastructure.http.HttpAddressFinderService;
import in.regalauction.infrastructure.persistence.inmemory.InMemoryAddressFinderService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpAddressFinderServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpAddressFinderServiceTest.class);

	/**
	 * @deprecated
	 * This was previuosly used to test the external http call to get state, city info.
	 * Now the state, city database is maintained in memory.
	 * @see InMemoryAddressFinderService
	 */
//	@Test
	public void testGetStates() {
		HttpAddressFinderService addressFinderService = new HttpAddressFinderService();
		List<String> states = addressFinderService.getStates();
		assertNotNull(states);
		assertTrue(!states.isEmpty());
		LOGGER.info("States found:");
		for (String state : states) {
			LOGGER.info(state);
			assertNotNull(state);
		}
	}

}
