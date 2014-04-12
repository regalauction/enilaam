<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="url"%>
<%@ attribute name="type" description="submit|button|link"%>
<%@ attribute name="labelCode" required="true"%>
<%@ attribute name="imgCode"%>
<%@ attribute name="cssClass" description="positive|negative"%>

<c:if test="${empty type}"><c:set var="type" value="button"/></c:if>
<c:if test="${empty imgCode and labelCode eq 'form.button.edit'}"><c:set var="imgCode" value="icon.button.edit"/></c:if>
<c:choose>
	<c:when test="${type eq 'link' or not empty url}">
		<a class="button ${cssClass}" href="${url}">
			<c:if test="${not empty imgCode}"><img src="<spring:theme code="${imgCode}"/>" alt=""/></c:if>
			<span><spring:message code="${labelCode}" text="${labelCode}"/></span>
		</a>
	</c:when>
	<c:otherwise>
		<c:if test="${type eq 'submit'}">
			<c:if test="${empty imgCode}"><c:set var="imgCode" value="icon.button.tick"/></c:if>
			<c:if test="${empty cssClass}"><c:set var="cssClass" value="positive"/></c:if>
		</c:if>
		<button type="${type}" class="button ${cssClass}">
			<c:if test="${not empty imgCode}"><img src="<spring:theme code="${imgCode}"/>" alt=""/></c:if>
			<span><spring:message code="${labelCode}" text="${labelCode}"/></span>
		</button>
	</c:otherwise>
</c:choose>