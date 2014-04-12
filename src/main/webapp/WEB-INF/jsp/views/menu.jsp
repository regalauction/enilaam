<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:importAttribute name="cssClass"/>
<ul class="${cssClass}">
	<li>
		<a href="<spring:url value="/home" />"><spring:message code="menu.home"/></a>
	</li>
	<li>
		<span class="dir"><spring:message code="menu.account"/></span>
		<ul>
			<li><a href="<spring:url value="/profile" />"><spring:message code="menu.account.profile"/></a></li>
			<security:authorize ifAnyGranted="ROLE_BIDDER">
			<li><a href="<spring:url value="/auction/history" />"><spring:message code="account.auctionhistory"/></a></li>
			</security:authorize>
			<li><a href="<c:url value="/j_spring_security_logout" />"><spring:message code="logout" /></a></li>
		</ul>
	</li>
	<li>
		<span class="dir"><spring:message code="menu.auction"/></span>
		<ul>
			<security:authorize ifAnyGranted="ROLE_BIDDER,ROLE_OBSERVER">
			<li><a href="<spring:url value="/auction/bid" />"><spring:message code="auction.running"/></a></li>
			</security:authorize>
			<li><a href="<spring:url value="/auction/upcomming" />"><spring:message code="auction.upcomming"/></a></li>
			
			<security:authorize ifAnyGranted="ROLE_ADMIN">
			<li><a href="<spring:url value="/auction/new" />"><spring:message code="menu.auction.new"/></a></li>
			<li><a href="<spring:url value="/auction" />"><spring:message code="auction.list"/></a></li>
			</security:authorize>
		</ul>
	</li>
	<security:authorize ifAnyGranted="ROLE_ADMIN">
	<li>
		<span class="dir"><spring:message code="menu.user"/></span>
		<ul>
			<li><a href="<spring:url value="/user/new" />"><spring:message code="menu.user.new"/></a></li>
			<li><a href="<spring:url value="/user" />"><spring:message code="user.list"/></a></li>
		</ul>
	</li>
	<li>
		<span class="dir"><spring:message code="menu.item"/></span>
		<ul>
			<li><a href="<spring:url value="/item/new" />"><spring:message code="menu.item.new"/></a></li>
			<li><a href="<spring:url value="/item" />"><spring:message code="item.list"/></a></li>
		</ul>
	</li>
	</security:authorize>
	<li>
		<a href="<spring:url value="/about" />"><spring:message code="menu.about"/></a>
	</li>
</ul>
