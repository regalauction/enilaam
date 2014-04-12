package in.regalauction.interfaces.web.user;


import org.hibernate.validator.constraints.NotBlank;

public class ChangePassword extends Password {

	@NotBlank private String oldPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}
