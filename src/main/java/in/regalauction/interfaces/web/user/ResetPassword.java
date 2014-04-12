package in.regalauction.interfaces.web.user;

public class ResetPassword extends Password {

	private String code;
	
	public ResetPassword() {
		super();
	}

	public ResetPassword(String code) {
		super();
		this.code = code;
	}

	public String getCode() {
		return code;
	}	
}
