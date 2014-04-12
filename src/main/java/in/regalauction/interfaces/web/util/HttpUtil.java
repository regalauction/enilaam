package in.regalauction.interfaces.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.commons.lang.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	
	private static final int STATUSCODE_OK = 200;
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

	private HttpUtil() {
		// Non instantiable utility class
	}
	
	public static String getResponse(final String uri) {

		HttpClient httpClient = new DefaultHttpClient();
		String responseString = "";
		
		try {
			HttpGet httpGet = new HttpGet(uri);
			
			LOGGER.info("Executing request: {}", httpGet.getURI());
			
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != STATUSCODE_OK) throw new RuntimeException("Response status not OK");
			
			HttpEntity entity = response.getEntity();
			Validate.notNull(entity);
			
			InputStream inputStream = entity.getContent();
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				responseString = reader.readLine();
				LOGGER.trace("Response: " + responseString);
			} catch (RuntimeException e) {
				httpGet.abort();
				throw e;
			} finally {
				inputStream.close();
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	
		return responseString;
	}
}
