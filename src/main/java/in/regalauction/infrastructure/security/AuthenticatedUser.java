package in.regalauction.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;

import in.regalauction.domain.model.user.User;


public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User domainUser;

	AuthenticatedUser(final UserDetails springSecurityUser, 
			final User domainUser) {
	
		super(springSecurityUser.getUsername(), 
				springSecurityUser.getPassword(), 
				springSecurityUser.isEnabled(), 
				springSecurityUser.isAccountNonExpired(), 
				springSecurityUser.isCredentialsNonExpired(),
				springSecurityUser.isAccountNonLocked(), 
				springSecurityUser.getAuthorities());
		
		this.domainUser = domainUser; 
	}

	public User getDomainUser() {
		return domainUser;
	}

}
