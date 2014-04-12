package in.regalauction.domain.model.user;

import java.io.Serializable;

import in.regalauction.domain.model.types.Address;
import in.regalauction.domain.shared.Entity;

import org.apache.commons.lang.StringUtils;



/**
 * This class represents a user.
 * 
 * @author Diptangshu Chakrabarty
 *
 */
public class User implements Entity<User>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean enabled;
	private String firstName;
	private String lastName;
	
	private String organization;
	private String contactPersonName;
	private String contactPersonDesignation;
	private String contactNumber;
	private Address address;
	
	private String fax;
	private String pan;
	private String vat;
	private String cst;
	private String ecc;
	private String bankDetails;
	private String userType;
	
	@Override
	public boolean sameIdentityAs(User other) {
		return other != null && this.username.equals(other.username);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		final User other = (User) obj;
		return sameIdentityAs(other);
	}
	
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getUsername() {
		return username;
	}

	public Address getAddress() {
		return address;
	}
	
	public void setAddress(final String address,
			final String city,
			final String state,
			final String pincode) {
		
		this.address = new Address(address, city, state, pincode);
	}
	
	public boolean isStateEmpty() {
		return address == null || StringUtils.isEmpty(address.getState());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonDesignation() {
		return contactPersonDesignation;
	}

	public void setContactPersonDesignation(String contactPersonDesignation) {
		this.contactPersonDesignation = contactPersonDesignation;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getEcc() {
		return ecc;
	}

	public void setEcc(String ecc) {
		this.ecc = ecc;
	}

	public String getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}


	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return username;
	}

	// Auto generated surrogate key
	private Long id;
	User() {
		// Needed by Hibernate
	}
	protected User(String username) {
		// Needed for test cases
		this.username = username;
	}
}
