package in.regalauction.infrastructure.http;

import in.regalauction.application.AddressFinderService;
import in.regalauction.interfaces.web.util.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpAddressFinderService implements AddressFinderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpAddressFinderService.class);
	
	private static final String URI_STATES = "http://www.mapsofindia.com/pincode/data.php?get=state";
	private static final String URI_CITIES = "http://www.mapsofindia.com/pincode/data.php?get=district&state=";

	private List<String> states;
	private Map<String, List<String>> cities = new HashMap<String, List<String>>();
	
	@Override
	public List<String> getStates() {
		return states == null? 
				states = asList(HttpUtil.getResponse(URI_STATES)) 
				: states;
	}
	
	@Override
	public List<String> getCities(final String state) {
		if (!cities.containsKey(state)) {
			String param;
			try {
				param = URLEncoder.encode(state, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
			List<String> list = asList(HttpUtil.getResponse(URI_CITIES.concat(param)));
			cities.put(state, list);
			return list;
		} else return cities.get(state);
	}
	
	private List<String> asList(String string) {
		String lowerCase = string.toLowerCase();
		LOGGER.trace("lowercase: {}", lowerCase);
		String capitalizeFully = WordUtils.capitalizeFully(lowerCase, "' ".toCharArray());
		LOGGER.trace("capitalized: {}", capitalizeFully);
		return Arrays.asList(capitalizeFully
				.replace("['", "")
				.replace("',]", "")
				.split("'\\s*,\\s*'"));
	}

}
