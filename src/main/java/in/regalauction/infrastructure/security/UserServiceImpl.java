package in.regalauction.infrastructure.security;

import in.regalauction.application.MailingService;
import in.regalauction.application.UserService;
import in.regalauction.domain.model.user.DuplicateUserException;
import in.regalauction.domain.model.user.User;
import in.regalauction.domain.model.user.UserRepository;
import in.regalauction.interfaces.web.user.UserForm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class UserServiceImpl extends JdbcUserDetailsManager implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private static final int PASSWORD_LENGTH = 8;
	private static final String DEBUG_PASSWORD = "passw0rd";
	
	public static final String DEF_FIND_GROUP_BY_USERNAME_SQL = 
			"select group_name from users u inner join group_members gm on gm.user_id = u.user_id " +
			"inner join groups g on g.id = gm.group_id where u.username = ?";
	public static final String DEF_UPDATE_ENABLED_SQL = 
			"update users set enabled = ? where username = ?";
	public static final String DEF_CREATE_RESET_PASSWORD_REQUEST_SQL = 
			"insert into resetpasswordreq(code, user_id, reqtime) select ?, user_id, current_timestamp from users where username = ?";
	public static final String DEF_RESET_PASSWORD_REQUEST_SQL = 
			"delete from resetpasswordreq where code = ?";
	public static final String DEF_FIND_USERNAME_FOR_RESET_PASSWORD_SQL = 
			"select u.username from users u inner join resetpasswordreq rpr on rpr.user_id = u.user_id " +
			"where rpr.reqtime > subdate(current_timestamp, interval 1 hour) and rpr.code = ?";
	public static final String DEF_GROUP_NAME_BIDDER = "BIDDER";
	public static final String DEF_GROUP_NAME_OBSERVER = "OBSERVER";
	
	private String findGroupByUsernameSql = DEF_FIND_GROUP_BY_USERNAME_SQL;
	private String findUsernameForResetPasswordSql = DEF_FIND_USERNAME_FOR_RESET_PASSWORD_SQL;
	private String updateEnabledSql = DEF_UPDATE_ENABLED_SQL;
	private String createResetPasswordRequestSql = DEF_CREATE_RESET_PASSWORD_REQUEST_SQL;
	private String deleteResetPasswordRequestSql = DEF_RESET_PASSWORD_REQUEST_SQL;
	private String changePasswordSql2 = DEF_CHANGE_PASSWORD_SQL;
	private String groupNameBidder = DEF_GROUP_NAME_BIDDER;
	private String groupNameObserver = DEF_GROUP_NAME_OBSERVER;
	
	// Domain user repository
	private UserRepository userRepository;
	
	private MailingService mailingService;
	// If set to true, a fixed password will be generated for all new users - needed during development
	private boolean samePasswordForAll = false;
	
	public void setUserRepository(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void setMailingService(MailingService mailingService) {
		this.mailingService = mailingService;
	}
	
	public void setFindGroupByUsernameSql(String findGroupByUsernameSql) {
		this.findGroupByUsernameSql = findGroupByUsernameSql;
	}

	public void setGroupNameBidder(String groupNameBidder) {
		this.groupNameBidder = groupNameBidder;
	}
	
	public void setUpdateEnabledSql(String updateEnabledSql) {
		this.updateEnabledSql = updateEnabledSql;
	}
	
	public void setCreateResetPasswordRequestSql(String createResetPasswordRequestSql) {
		this.createResetPasswordRequestSql = createResetPasswordRequestSql;
	}
	
	public void setFindUsernameForResetPasswordSql(String findUsernameForResetPasswordSql) {
		this.findUsernameForResetPasswordSql = findUsernameForResetPasswordSql;
	}
	
	public void setDeleteResetPasswordRequestSql(String deleteResetPasswordRequestSql) {
		this.deleteResetPasswordRequestSql = deleteResetPasswordRequestSql;
	}
	
	public void setSamePasswordForAll(boolean samePasswordForAll) {
		this.samePasswordForAll = samePasswordForAll;
	}

	@Override
	public void setChangePasswordSql(String changePasswordSql) {
		this.changePasswordSql2 = changePasswordSql; 
		super.setChangePasswordSql(changePasswordSql);
	}
	
	@Override
	public String getCurrentUsername() {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		return currentUser.getName();
	}

	@Override
	public User getCurrentUser() {
		return findByUsername(getCurrentUsername());
	}

	@Override
	public void createUser(final UserForm userForm) throws DuplicateUserException {
		LOGGER.trace("Creating user...");
		
		String groupName = userForm.getRole();
		String username = userForm.getUsername();
		String password = generatePassword();
		
		if (findByUsername(username) != null)
			throw new DuplicateUserException();
		
		LOGGER.debug("Saving user details for Spring Security");
		createUser(new org.springframework.security.core.userdetails.User(username, password, findGroupAuthorities(groupName)));
		addUserToGroup(username, groupName);
		
		updateUserInRepository(userForm);
		
		String toAddress = "regalauction@gmail.com";
//		String toAddress = username;
		mailingService.sendWelcomeMail(toAddress, userForm.getFirstName(), password);
	}

	private String generatePassword() {
		return samePasswordForAll? DEBUG_PASSWORD : RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
	}

	@Override
	public void updateUser(final UserForm userForm) {
		LOGGER.trace("updating user...");
		
		final String username = userForm.getUsername();
		String newGroupName = userForm.getRole();
		
		LOGGER.debug("Updating enabled status");
		
		Validate.notEmpty(username);
		getJdbcTemplate().update(updateEnabledSql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setBoolean(1, userForm.isEnabled());
				ps.setString(2, username);
			}
		});
		
		LOGGER.trace("Removing user from existing group");
		
		for (String groupName : findAllGroups())
			removeUserFromGroup(username, groupName);
		
		addUserToGroup(username, newGroupName);
		
		updateUserInRepository(userForm);
	}
	
	private void updateUserInRepository(final UserForm userForm) {
		User user = userRepository.findByUsername(userForm.getUsername());
		
		BeanUtils.copyProperties(userForm, user, new String[] { "username", "address" });
		user.setAddress(userForm.getAddress(), userForm.getCity(), userForm.getState(), userForm.getPincode());
		
		userRepository.store(user);
	}
	
	@Override
	public String findGroupByUsername(String username) {
		LOGGER.trace("fetching group name of user {}", username);
		return getJdbcTemplate().queryForList(findGroupByUsernameSql, new String[] {username}, String.class).get(0);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Collection<User> findBidders() {
		return findByGroupName(groupNameBidder);
	}
	
	@Override
	public Collection<User> findObservers() {
		return findByGroupName(groupNameObserver);
	}
	
	private Collection<User> findByGroupName(final String groupName) {
		LOGGER.trace("fetching {}...", groupName);
		Collection<User> bidders = new LinkedList<User>();
		for (String username : findUsersInGroup(groupName)) {
			bidders.add(userRepository.findByUsername(username));
		}
		return bidders;
	
	}

	@Override
	public boolean isEnabled(String username) {
		return loadUserByUsername(username).isEnabled();
	}

	@Override
	public void sendResetPasswordMail(final String username, final String linkBaseUrl)
			throws UsernameNotFoundException {

		LOGGER.debug("Checking whether {} is a valid user", username);
		loadUserByUsername(username);
		LOGGER.debug("{} is a valid user", username);
		
		String string = UUID.randomUUID().toString();
		final String code = string.substring(0, string.indexOf("-"));
		
		// Create a new reset password request
		 getJdbcTemplate().update(createResetPasswordRequestSql, new PreparedStatementSetter() {
			 public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, code);
                ps.setString(2, username);
            }
        });
		
		mailingService.sendResetPasswordMail(username, linkBaseUrl.concat(code));
	}

	@Override
	public String findUsernameForResetPassword(String code) throws InvalidResetPasswordException {
		
		List<String> usernames = getJdbcTemplate().queryForList(findUsernameForResetPasswordSql, new String[] {code}, String.class);
		
		if (usernames.isEmpty()) throw new InvalidResetPasswordException();
		
		String username = usernames.get(0);
		
		return username;
	}
	
	@Override
	public void resetPassword(String code, String newPassword) throws InvalidResetPasswordException {
		
		LOGGER.trace("Resetting password for password request code {}", code);
		
		String username = findUsernameForResetPassword(code);
		
		LOGGER.debug("Updating new password");
		getJdbcTemplate().update(changePasswordSql2, newPassword, username);
		
		LOGGER.debug("Invalidating reset password request");
		getJdbcTemplate().update(deleteResetPasswordRequestSql, code);
		
	}
	
	/**
	 * The following method is used to put the domain user object in spring security context
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		LOGGER.trace("Preparing the custom User object to set in Security Context");

		UserDetails springSecurityUser = super.loadUserByUsername(username);
		User domainUser = userRepository.findByUsername(username);
		
		return new AuthenticatedUser(springSecurityUser, domainUser);
	}

}
