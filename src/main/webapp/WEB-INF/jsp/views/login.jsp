<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<form action="j_spring_security_check" method="post" id="login">
	<fieldset>
		<legend>Login</legend>
		
		<c:if test="${param.error eq 'true'}">
			<div class="error"><spring:message code="login.authenticationFailure" /></div>
		</c:if>
		
		<label for="j_username"><spring:message code="login.username" /></label>
		<input type="text" id="j_username" name="j_username" class="wide"/>
	
		<label for="j_password"><spring:message code="login.password" /></label>
		<input type="password" id="j_password" name="j_password" class="wide"/>
	
		<div class="prepend-3">
			<custom:button type="submit" labelCode="login.submit" imgCode="icon.button.key"/>
		</div>
		<div class="clear prepend-3">
			<spring:url value="/forgotPassword" var="forgotPasswordUrl"/>
			<a href="${forgotPasswordUrl}"><spring:message code="login.forgotPassword"/></a>
		</div>
	</fieldset>
</form>

<div>
<p>
We are pleased to introduce our organization, <strong><spring:message code="appname"/></strong>, offering an array of services that include e-Selling, e-Sourcing through e-Auction platform across diverse industry verticals, empowering businesses with greater process efficiencies.
</p>
<p>
Today&acute;s economic trends have changed forcing many companies and individuals to downsize or reorganize their processes in order to meet their bottom line goals. We provide flexible end-to-end solutions that assist companies and individuals to meet their objectives by giving the best possible service at an affordable fee.
</p>

<p>Please check the <a href="<spring:url value="services" />">services</a> we offer.
</div>
