<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<h3><spring:message code="welcome" arguments="${user.firstName},${user.lastName}"/></h3>

<security:authorize ifAnyGranted="ROLE_BIDDER">
<c:if test="${numRunningAuctions gt 0}">
	<p>You can place bid on <a href="<spring:url value="/auction/bid" />"><c:out value="${numRunningAuctions}"/> running auctions.</a></p>
</c:if>
</security:authorize>

<security:authorize ifAnyGranted="ROLE_OBSERVER">
<c:if test="${numRunningAuctions gt 0}">
	<p>You can observe <a href="<spring:url value="/auction/bid" />"><c:out value="${numRunningAuctions}"/> running auctions.</a></p>
</c:if>
</security:authorize>

<c:if test="${hasUpcommingAuctions}">
	<p>Check <a href="<spring:url value="/auction/upcomming" />">upcomming auctions</a> on <spring:message code="appname" />.</p>
</c:if>

<security:authorize ifAnyGranted="ROLE_BIDDER">
<p>
<c:choose>
	<c:when test="${hasAuctionHistory}">
		Find the auctions you have participated previously in your <a href="<spring:url value="/auction/history" />">auction history.</a>
	</c:when>
	<c:otherwise>You have not participated in any auctions yet.</c:otherwise>
</c:choose>
</p>
</security:authorize>

<p>Frequently <a href="<spring:url value="/profile" />">change your password</a> for security reasons.</p>