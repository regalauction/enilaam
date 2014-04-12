<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<spring:url value="/login" var="loginUrl"/>
<h3>Mail Sent!</h3>
<p>Reset password link was sent to <span class="loud"><c:out value="${email}"/></span>.</p>
<p>Please use that link to reset your password and <a href="${loginUrl}">login</a>.</p>
