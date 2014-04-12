<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:url value="/home" var="homeUrl"/>
<h2><a href="${homeUrl}" style="text-decoration: none; color: black;"><img border="0" alt="<spring:message code="appname" />" src="<spring:theme code="image.logo" />"> </a></h2>
