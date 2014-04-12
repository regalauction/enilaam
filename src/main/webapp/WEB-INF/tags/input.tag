<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="labelCode" %>
<%@ attribute name="cssClass" %>
<%@ attribute name="required" type="java.lang.Boolean" %>
<%@ attribute name="noUpdate" type="java.lang.Boolean" %>
<%@ attribute name="type" %>

<c:if test="${not empty labelCode}">
	<custom:label labelCode="${labelCode}" forPath="${path}" required="${not noUpdate and required}"/>
</c:if>
<spring:bind path="${path}">
	<c:choose>
		<c:when test="${noUpdate}">
			<span class="form-field-static"><c:out value="${status.value}"/></span><br />
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${type eq 'password'}">
					<form:password path="${path}" cssClass="${cssClass}" cssErrorClass="${cssClass} form-error-field"/>
				</c:when>
				<c:otherwise>
					<form:input path="${path}" cssClass="${cssClass}" cssErrorClass="${cssClass} form-error-field"/>
				</c:otherwise>
			</c:choose>
			<form:errors path="${path}" cssClass="form-error-msg"/><br />
		</c:otherwise>
	</c:choose>
</spring:bind>
