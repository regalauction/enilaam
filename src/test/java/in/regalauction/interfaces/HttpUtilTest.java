package in.regalauction.interfaces;

import static org.junit.Assert.assertFalse;
import in.regalauction.interfaces.web.util.HttpUtil;

import org.junit.Test;


public class HttpUtilTest {
	

	@Test
	public void testGetResponse() {
		String response = HttpUtil.getResponse("http://www.google.com");
		assertFalse(response.isEmpty());
	}

}
