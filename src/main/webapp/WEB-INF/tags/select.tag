<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="labelCode" %>
<%@ attribute name="cssClass" %>
<%@ attribute name="required" type="java.lang.Boolean" %>
<%@ attribute name="noUpdate" type="java.lang.Boolean" description="if 'true' then displays static text if path has value." %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>
<%@ attribute name="itemLabel" %>
<%@ attribute name="itemValue" %>

<c:if test="${not empty labelCode}">
	<custom:label labelCode="${labelCode}" forPath="${path}"/>
</c:if>
<spring:bind path="${path}">
	<c:choose>
		<c:when test="${noUpdate and not empty status.value}">
			<span class="form-field-static"><c:out value="${status.value}"/></span><br />
		</c:when>
		<c:otherwise>
			<form:select path="${path}" cssClass="${cssClass}" cssErrorClass="form-error-field">
				<spring:theme code="form.select.label" var="label"/>
				<c:if test="${not required}"><form:option value="" label="${label}"/></c:if>
				<c:choose>
					<c:when test="${not empty itemLabel and not empty itemValue}">
						<form:options items="${items}" itemLabel="${itemLabel}" itemValue="${itemValue}"/>
					</c:when>
					<c:otherwise>
						<form:options items="${items}"/>
					</c:otherwise>
				</c:choose>
			</form:select>
			<form:errors path="${path}" cssClass="form-error-msg"/><br />
		</c:otherwise>
	</c:choose>
</spring:bind>