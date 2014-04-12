<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<form:form modelAttribute="resetPassword">
<fieldset>
	<legend>Change Password</legend>
	<form:errors/>

	<p>You requested to change your password for your account with username <span class="loud"><c:out value="${username}"/></span></p>
	<form:hidden path="code"/>
	
	<custom:input path="newPassword" labelCode="userForm.newpassword" type="password"/>
	<custom:input path="retypePassword" labelCode="userForm.retypepassword" type="password"/>
	
	<div class="prepend-3">
		<custom:button type="submit" labelCode="userForm.changepassword" imgCode="icon.button.tick"/>
	</div>
</fieldset>
</form:form>
	