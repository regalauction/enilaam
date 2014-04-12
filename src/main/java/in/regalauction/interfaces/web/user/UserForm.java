package in.regalauction.interfaces.web.user;

import in.regalauction.domain.model.user.User;

import java.lang.reflect.InvocationTargetException;

import javax.validation.constraints.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class UserForm {
	
	@NotBlank 
	@Email
	private String username;
	@NotBlank private String role;
	private boolean enabled;
	
	private String firstName;
	private String lastName;
	
	private String organization;
	private String contactPersonName;
	private String contactPersonDesignation;
	
	@Pattern(regexp = "\\s*|\\d{8,15}")
	private String contactNumber;
	private String address;
	private String city;
	private String state;
	
	private String fax;
	private String pan;
	private String vat;
	private String cst;
	private String ecc;
	private String bankDetails;
	private String userType;
	
	@Pattern(regexp = "\\s*|\\d{6}")
	private String pincode;
	
	private boolean existing;
	
	public UserForm(User user, String groupName, boolean enabled) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.copyProperties(this, user);
		if (user.getAddress() != null) BeanUtils.copyProperties(this, user.getAddress());
		existing = true;
		this.role = groupName;
		this.enabled = enabled;
	}

	public UserForm() {
		existing = false;
	}
	
	public boolean isExisting() {
		return existing;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public void setPincode(String pincode) {
		this.pincode = pincode;
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
}
