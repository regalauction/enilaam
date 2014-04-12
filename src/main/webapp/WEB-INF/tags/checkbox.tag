<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<%@ attribute name="path" required="true" %>
<%@ attribute name="labelCode" %>
<%@ attribute name="required" type="java.lang.Boolean" %>
<%@ attribute name="noUpdate" type="java.lang.Boolean" description="if 'true' then displays static tick." %>

<c:if test="${not empty labelCode}">
	<custom:label labelCode="${labelCode}" forPath="${path}" required="${not noUpdate and required}"/>
</c:if>
<spring:bind path="${path}">
	<c:choose>
		<c:when test="${noUpdate}">
			<span class="form-field-static"><img src="<spring:theme code="image.button.icon.tick"/>" alt=""/></span><br />
		</c:when>
		<c:otherwise>
			<form:checkbox path="${path}" id="${path}" cssErrorClass="form-error-field"/>
			<form:errors path="${path}" cssClass="form-error-msg"/><br />
		</c:otherwise>
	</c:choose>
</spring:bind>