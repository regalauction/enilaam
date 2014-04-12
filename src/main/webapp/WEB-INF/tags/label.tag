<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ attribute name="forPath" required="true" %>
<%@ attribute name="labelCode" %>
<%@ attribute name="required" type="java.lang.Boolean" %>

<label for="${forPath}">
	<spring:message code="${labelCode}" text="${labelCode}"/>
	<c:if test="${required}"><em class="form-field-req" title="<spring:message code="form.title.required"/>">*</em></c:if>
</label>
