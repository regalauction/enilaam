package in.regalauction.interfaces.web.user;

import javax.validation.constraints.Size;

public class Password {

	@Size(min = 6, max = 12)
	private String newPassword;
	private String retypePassword;

	public Password() {
		super();
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String reTypePassword) {
		this.retypePassword = reTypePassword;
	}

}