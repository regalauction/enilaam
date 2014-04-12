<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<div class="span-10 colborder">
	<spring:url value="/profile/changePassword" var="actionUrl"/>
	<form:form action="${actionUrl}" modelAttribute="changePassword">
	<form:errors/>
		
		<custom:input path="oldPassword" labelCode="userForm.oldpassword" type="password"/>
		<custom:input path="newPassword" labelCode="userForm.newpassword" type="password"/>
		<custom:input path="retypePassword" labelCode="userForm.retypepassword" type="password"/>
		
		<div class="prepend-3">
			<custom:button type="submit" labelCode="userForm.changepassword" imgCode="icon.button.tick"/>
		</div>
	</form:form>
</div>
<div class="span-13 last">
	<table>
		<c:if test="${not empty user.firstName || not empty user.lastName}">
		<tr><th>Name</th><td><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td></tr>
		</c:if>
		
		<tr><th>Email</th><td><c:out value="${user.username}"/></td></tr>
		
		<c:if test="${not empty user.organization}">
		<tr><th><spring:message code="user.organization"/></th><td><c:out value="${user.organization}"/></td></tr>
		</c:if>
		
		<c:if test="${not empty user.contactPersonName}">
		<tr><th><spring:message code="user.contactPersonName"/></th><td><c:out value="${user.contactPersonName}"/></td></tr>
		</c:if>
		
		<c:if test="${not empty user.contactNumber}">
		<tr><th><spring:message code="user.contactNumber"/></th><td><c:out value="${user.contactNumber}"/></td></tr>
		</c:if>
		
		<c:if test="${not empty user.fax}">
		<tr><th><spring:message code="user.fax"/></th><td><c:out value="${user.fax}"/></td></tr>
		</c:if>
		
		<c:set var="address" value="${user.address}"/>
		<c:if test="${not empty address.address || not empty address.city || not empty address.state || not empty address.pincode}">
		<tr>
			<th><spring:message code="user.address"/></th>
			<td>
				<c:if test="${not empty address.address}"><c:out value="${address.address}"/>,<br /></c:if>
				<c:if test="${not empty address.city}"><c:out value="${address.city}"/>,</c:if>
				<c:if test="${not empty address.state}"><c:out value="${address.state}"/>,<br /></c:if>
				<c:if test="${not empty address.pincode}"><spring:message code="user.pincode"/>: <c:out value="${address.pincode}"/></c:if>
			</td>
		</tr>
		</c:if>
	</table>
</div>