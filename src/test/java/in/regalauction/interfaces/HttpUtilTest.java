package in.regalauction.interfaces;

import static org.junit.Assert.*;
import in.regalauction.interfaces.web.util.HttpUtil;

import org.junit.Test;


public class HttpUtilTest {
	

	@Test
	public void testGetResponse() {
		String response = HttpUtil.getResponse("http://www.mapsofindia.com/pincode/data.php?get=state");
		assertFalse(response.isEmpty());
		assertTrue(response.matches("^\\[('.+',)+\\]$"));
	}

}
