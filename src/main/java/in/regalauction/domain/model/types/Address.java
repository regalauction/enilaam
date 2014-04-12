package in.regalauction.domain.model.types;

import in.regalauction.domain.shared.ValueObject;

import org.apache.commons.lang.builder.EqualsBuilder;


public class Address implements ValueObject<Address> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String address;
	private String city;
	private String state;
	private String pincode;
	
	public Address(final String address, final String city, final String state, final String pincode) {
		
		this.address = address;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	@Override
	public boolean sameValueAs(final Address other) {
		return other != null
				&& new EqualsBuilder()
				.append(this, other.address)
				.append(this, other.city)
				.append(this, other.state)
				.append(this, other.pincode)
				.isEquals();
					
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String country) {
		this.pincode = country;
	}
	
	Address() {
		// Needed by Hibernate
	}
}
