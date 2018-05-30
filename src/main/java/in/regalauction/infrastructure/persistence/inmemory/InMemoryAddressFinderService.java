package in.regalauction.infrastructure.persistence.inmemory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.application.AddressFinderService;


public class InMemoryAddressFinderService implements AddressFinderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryAddressFinderService.class);

	private static Map<String, String[]> geoMap;
	static {
		geoMap = new TreeMap<String, String[]>();
		geoMap.put("Andaman & Nicobar Islands", new String[] {"Nicobar", "North And Middle Andaman", "South Andaman"});
		geoMap.put("Andhra Pradesh", new String[] {"Adilabad", "Anantapur", "Chittoor", "Cuddapah", "East Godavari", "Guntur", "Hyderabad", "Karim Nagar", "Khammam", "Krishna", "Kurnool", "Mahabubnagar", "Medak", "Nalgonda", "Nellore", "Nizamabad", "Prakasam", "Rangareddi", "Srikakulam", "Visakhapatnam", "Vizianagaram", "Warangal", "West Godavari"});
		geoMap.put("Arunachal Pradesh", new String[] {"Changlang", "Dibang Valley", "East Kameng", "East Siang", "Kurung Kumey", "Lohit", "Lower Dibang Valley", "Lower Subansiri", "Papum Pare", "Tawang", "Tirap", "Upper Siang", "Upper Subansiri", "West Kameng", "West Siang"});
		geoMap.put("Assam", new String[] {"Barpeta", "Bongaigaon", "Cachar", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Goalpara", "Golaghat", "Hailakandi", "Jorhat", "Kamrup", "Karbi Anglong", "Karimganj", "Kokrajhar", "Lakhimpur", "Marigaon", "Nagaon", "Nalbari", "North Cachar Hills", "Sivasagar", "Sonitpur", "Tinsukia"});
		geoMap.put("Bihar", new String[] {"Araria", "Aurangabad", "Banka", "Begusarai", "Bhagalpur", "Bhojpur", "Buxar", "Darbhanga", "East Champaran", "Gaya", "Gopalganj", "Jamui", "Jehanabad", "Kaimur", "Katihar", "Khagaria", "Kishanganj", "Lakhisarai", "Madhepura", "Madhubani", "Munger", "Muzaffarpur", "Nalanda", "Nawada", "Patna", "Purnia", "Rohtas", "Saharsa", "Samastipur", "Saran", "Sheikhpura", "Sheohar", "Sitamarhi", "Siwan", "Supaul", "Vaishali", "West Champaran"});
		geoMap.put("Chandigarh", new String[] {"Chandigarh"});
		geoMap.put("Chhattisgarh", new String[] {"Bastar", "Bijapur", "Bilaspur", "Dantewada", "Dhamtari", "Durg", "Janjgir-champa", "Jashpur", "Kanker", "Kawardha", "Korba", "Koriya", "Mahasamund", "Narayanpur", "Raigarh", "Raipur", "Rajnandgaon", "Surguja"});
		geoMap.put("Dadra & Nagar Haveli", new String[] {"Dadra & Nagar Haveli"});
		geoMap.put("Daman & Diu", new String[] {"Daman", "Diu"});
		geoMap.put("Delhi", new String[] {"Central Delhi", "East Delhi", "New Delhi", "North Delhi", "North West Delhi", "South Delhi", "South West Delhi", "West Delhi"});
		geoMap.put("Goa", new String[] {"North Goa", "South Goa"});
		geoMap.put("Gujarat", new String[] {"Ahmedabad", "Amreli", "Anand", "Banaskantha", "Bharuch", "Bhavnagar", "Dahod", "Gandhi Nagar", "Jamnagar", "Junagadh", "Kachchh", "Kheda", "Mahesana", "Narmada", "Navsari", "Panch Mahals", "Patan", "Porbandar", "Rajkot", "Sabarkantha", "Surat", "Surendra Nagar", "The Dangs", "Vadodara", "Valsad"});
		geoMap.put("Haryana", new String[] {"Ambala", "Bhiwani", "Faridabad", "Fatehabad", "Gurgaon", "Hisar", "Jhajjar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Mahendragarh", "Panchkula", "Panipat", "Rewari", "Rohtak", "Sirsa", "Sonipat", "Yamuna Nagar"});
		geoMap.put("Himachal Pradesh", new String[] {"Bilaspur", "Chamba", "Hamirpur", "Kangra", "Kinnaur", "Kullu", "Lahul & Spiti", "Mandi", "Shimla", "Sirmaur", "Solan", "Una"});
		geoMap.put("Jammu & Kashmir", new String[] {"Ananthnag", "Baramulla", "Budgam", "Doda", "Jammu", "Kargil", "Kathua", "Kupwara", "Leh", "Poonch", "Pulwama", "Rajauri", "Srinagar", "Udhampur"});
		geoMap.put("Jharkhand", new String[] {"Bokaro", "Chatra", "Deoghar", "Dhanbad", "Dumka", "East Singhbhum", "Garhwa", "Giridh", "Godda", "Gumla", "Hazaribag", "Jamtara", "Koderma", "Latehar", "Lohardaga", "Pakur", "Palamau", "Ramgarh", "Ranchi", "Sahibganj", "Seraikela-kharsawan", "Simdega", "West Singhbhum"});
		geoMap.put("Karnataka", new String[] {"Bagalkot", "Bangalore", "Bangalore Rural", "Belgaum", "Bellary", "Bidar", "Bijapur", "Chamrajnagar", "Chickmagalur", "Chitradurga", "Dakshina Kannada", "Davangare", "Dharward", "Gadag", "Gulbarga", "Hassan", "Haveri", "Kodagu", "Kolar", "Koppal", "Mandya", "Mysore", "Raichur", "Shimoga", "Tumkur", "Udupi", "Uttara Kannada"});
		geoMap.put("Kerala", new String[] {"Alappuzha", "Ernakulam", "Idukki", "Kannur", "Kasargod", "Kollam", "Kottayam", "Kozhikode", "Malappuram", "Palakkad", "Pathanamthitta", "Thiruvananthapuram", "Thrissur", "Wayanad"});
		geoMap.put("Lakshadweep", new String[] {"Lakshadweep"});
		geoMap.put("Madhya Pradesh", new String[] {"Anuppur", "Ashoknagar", "Balaghat", "Barwani", "Betul", "Bhind", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh", "Datia", "Dewas", "Dhar", "Dindori", "Guna", "Gwalior", "Harda", "Hoshangabad", "Indore", "Jabalpur", "Jhabua", "Katni", "Khandwa", "Khargone", "Mandla", "Mandsaur", "Morena", "Narsinghpur", "Neemuch", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur", "Sheopur", "Shivpuri", "Sidhi", "Tikamgarh", "Ujjain", "Umaria", "Vidisha"});
		geoMap.put("Maharashtra", new String[] {"Ahmednagar", "Akola", "Amravati", "Aurangabad", "Beed", "Bhandara", "Buldhana", "Chandrapur", "Dhule", "Gadchiroli", "Gondia", "Hingoli", "Jalgaon", "Jalna", "Kolhapur", "Latur", "Mumbai", "Nagpur", "Nanded", "Nandurbar", "Nashik", "Osmanabad", "Parbhani", "Pune", "Raigad", "Ratnagiri", "Sangli", "Satara", "Sindhudurg", "Solapur", "Thane", "Wardha", "Washim", "Yavatmal"});
		geoMap.put("Manipur", new String[] {"Bishnupur", "Chandel", "Churachandpur", "Imphal East", "Imphal West", "Senapati", "Tamenglong", "Thoubal", "Ukhrul"});
		geoMap.put("Meghalaya", new String[] {"East Garo Hills", "East Khasi Hills", "Jaintia Hills", "Ri Bhoi", "South Garo Hills", "West Garo Hills", "West Khasi Hills"});
		geoMap.put("Mizoram", new String[] {"Aizawl", "Champhai", "Kolasib", "Lawngtlai", "Lunglei", "Mammit", "Saiha", "Serchhip"});
		geoMap.put("Nagaland", new String[] {"Dimapur", "Kiphire", "Kohima", "Longleng", "Mokokchung", "Mon", "Peren", "Phek", "Tuensang", "Wokha", "Zunhebotto"});
		geoMap.put("Orissa", new String[] {"Angul", "Balangir", "Baleswar", "Bargarh", "Bhadrak", "Boudh", "Cuttack", "Debagarh", "Dhenkanal", "Gajapati", "Ganjam", "Jagatsinghapur", "Jajapur", "Jharsuguda", "Kalahandi", "Kandhamal", "Kendrapara", "Kendujhar", "Khurda (khordha)", "Koraput", "Malkangiri", "Mayurbhanj", "Nabarangapur", "Nayagarh", "Nuapada", "Puri", "Rayagada", "Sambalpur", "Sonapur (subarnapur)", "Sundergarh"});
		geoMap.put("Pondicherry", new String[] {"Karaikal", "Mahe", "Pondicherry"});
		geoMap.put("Punjab", new String[] {"Amritsar", "Bathinda", "Faridkot", "Fatehgarh Sahib", "Firozpur", "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana", "Mansa", "Moga", "Mohali", "Muktsar", "Nawanshahr", "Patiala", "Ropar", "Sangrur"});
		geoMap.put("Rajasthan", new String[] {"Ajmer", "Alwar", "Banswara", "Baran", "Barmer", "Bharatpur", "Bhilwara", "Bikaner", "Bundi", "Chittorgarh", "Churu", "Dausa", "Dholpur", "Dungarpur", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalor", "Jhalawar", "Jhunjhunu", "Jodhpur", "Karauli", "Kota", "Nagaur", "Pali", "Rajsamand", "Sawai Madhopur", "Sikar", "Sirohi", "Sri Ganganagar", "Tonk", "Udaipur"});
		geoMap.put("Sikkim", new String[] {"East Sikkim", "North Sikkim", "South Sikkim", "West Sikkim"});
		geoMap.put("Tamil Nadu", new String[] {"Ariyalur", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram", "Kanyakumari", "Karur", "Krishnagiri", "Madurai", "Nagapattinam", "Namakkal", "Nilgiris", "Perambalur", "Pudukkottai", "Ramanathapuram", "Salem", "Sivaganga", "Thanjavur", "Theni", "Tiruchirappalli", "Tirunelveli", "Tiruvallur", "Tiruvannamalai", "Tiruvarur", "Tuticorin", "Vellore", "Villupuram", "Virudhunagar"});
		geoMap.put("Tripura", new String[] {"Dhalai", "North Tripura", "South Tripura", "West Tripura"});
		geoMap.put("Uttar Pradesh", new String[] {"Agra", "Aligarh", "Allahabad", "Ambedkar Nagar", "Auraiya", "Azamgarh", "Bagpat", "Bahraich", "Ballia", "Balrampur", "Banda", "Barabanki", "Bareilly", "Basti", "Bijnor", "Budaun", "Bulandshahr", "Chandauli", "Chitrakoot", "Deoria", "Etah", "Etawah", "Faizabad", "Farrukhabad", "Fatehpur", "Firozabad", "Gautam Buddha Nagar", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Jyotiba Phule Nagar", "Kannauj", "Kanpur Dehat", "Kanpur Nagar", "Kaushambi", "Kheri", "Kushinagar", "Lalitpur", "Lucknow", "Maharajganj", "Mahoba", "Mainpuri", "Mathura", "Mau", "Meerut", "Mirzapur", "Moradabad", "Muzaffarnagar", "Pilibhit", "Pratapgarh", "Raebareli", "Rampur", "Saharanpur", "Sant Kabir Nagar", "Sant Ravidas Nagar", "Shahjahanpur", "Shrawasti", "Siddharthnagar", "Sitapur", "Sonbhadra", "Sultanpur", "Unnao", "Varanasi"});
		geoMap.put("Uttarakhand", new String[] {"Almora", "Bageshwar", "Chamoli", "Champawat", "Dehradun", "Haridwar", "Nainital", "Pauri Garhwal", "Pithoragarh", "Rudraprayag", "Tehri Garhwal", "Udham Singh Nagar", "Uttarkashi"});
		geoMap.put("West Bengal", new String[] {"Bankura", "Bardhaman", "Birbhum", "Cooch Behar", "Darjeeling", "East Medinipur", "Hooghly", "Howrah", "Jalpaiguri", "Kolkata", "Malda", "Murshidabad", "Nadia", "North 24 Parganas", "North Dinajpur", "Purulia", "South 24 Parganas", "South Dinajpur", "West Medinipur", "West Midnapore"});
	}
	
	@Override
	public List<String> getStates() {
		LOGGER.trace("Fetching states...");
		return new ArrayList<String>(geoMap.keySet());
	}
	@Override
	public List<String> getCities(final String state) {
		LOGGER.trace("Fetching cities for state {}", state);
		return Arrays.asList(geoMap.get(state));
	}
}
