<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<form:form modelAttribute="forgotPassword">
<fieldset>
	<legend>Forgot Password</legend>
	<form:errors/>
	
	<custom:input path="username" labelCode="login.username"/>
	
	<div class="prepend-3">
		<custom:button type="submit" labelCode="forgotPassword.reset" imgCode="icon.button.key"/>
	</div>
</fieldset>
</form:form>
	