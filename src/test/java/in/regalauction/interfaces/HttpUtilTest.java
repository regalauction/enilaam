package in.regalauction.interfaces;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import in.regalauction.interfaces.web.util.HttpUtil;


public class HttpUtilTest {
	

	@Test
	public void testGetResponse() {
		String response = HttpUtil.getResponse("http://www.google.com");
		assertFalse(response.isEmpty());
	}

}
