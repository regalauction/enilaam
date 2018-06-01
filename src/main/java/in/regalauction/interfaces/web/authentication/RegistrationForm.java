package in.regalauction.interfaces.web.authentication;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class RegistrationForm {
	
	@NotBlank
	@Email
	private String email;
	
	@NotEmpty
	private String pan;
	private String cst;
	
	@NotEmpty
	private String vat;
	
	private String field1;
	private String field2;
	
	@NotEmpty
	private String banker;
	@NotEmpty
	private String accountNumber;
	@NotEmpty
	private String branch;
	private String branchCode;
	private String micrCode;
	private String ifscCode;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getCst() {
		return cst;
	}

	public void setCst(String cst) {
		this.cst = cst;
	}

	public String getVat() {
		return vat;
	}

	public void setVat(String vat) {
		this.vat = vat;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getBanker() {
		return banker;
	}

	public void setBanker(String banker) {
		this.banker = banker;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("\n").append("Email: ").append(email)
				.append("\n").append("PAN: ").append(pan)
				.append("\n").append("CST: ").append(cst)
				.append("\n").append("LST/TIN/VAT: ").append(vat)
				.append("\n").append("Excise Registration Number: ").append(field1)
				.append("\n").append("Factory License Number: ").append(field2)
				.append("\n").append("Banker: ").append(banker)
				.append("\n").append("Account Number: ").append(accountNumber)
				.append("\n").append("Branch: ").append(branch)
				.append("\n").append("Branch Code: ").append(branchCode)
				.append("\n").append("MICR: ").append(micrCode)
				.append("\n").append("IFSC Code: ").append(ifscCode).append("\n")
				.toString();
	}
	
	

}
