<!-- running.jsp -->
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tlds/util.tld" prefix="util"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<table class="table table-striped">
	<thead>
		<tr>
			<th><spring:message code="auction.auctionCode"/></th>
			<th><spring:message code="auction.name"/></th>
			<th><spring:message code="bidding.auction.timeleft"/></th>
			<th><spring:message code="auction.reserveprice"/></th>
			<th><spring:message code="auction.bidprice"/></th>
			<th><spring:message code="auction.bidtime"/></th>
			<th><spring:message code="bidder"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${auctions}" var="auction">
			<tr>
				<td>
					<security:authorize ifAnyGranted="ROLE_ADMIN">
						<spring:url value="/auction/{auctionCode}" var="editUrl">
							<spring:param name="auctionCode" value="${auction.auctionCode}"/>
						</spring:url>
						<a href="${editUrl}"><c:out value="${auction.auctionCode}"/></a>
					</security:authorize>
					<security:authorize ifNotGranted="ROLE_ADMIN">
						<c:out value="${auction.auctionCode}"/>
					</security:authorize>
				</td>
				<td><c:out value="${auction.name}"/></td>
				<td>
					<span <c:if test="${auction.goingToEnd}">class="text-warning"</c:if>>
						<util:period date="${auction.endDate}" />
					</span>
				</td>
				<td>
					<spring:message code="common.currencySymbol"/>&nbsp;
					<fmt:formatNumber value="${auction.reservePrice.amount}" groupingUsed="true" minFractionDigits="0" maxFractionDigits="2"/>
				</td>
				<c:choose>
					<c:when test="${auction.noBid}">
						<td colspan="3" class="center text-warning"><spring:message code="auction.nobids"/></td>
					</c:when>
					<c:otherwise>
						<td>
							<spring:message code="common.currencySymbol"/>&nbsp;
							<fmt:formatNumber value="${auction.leadPrice.amount}" groupingUsed="true" minFractionDigits="0" maxFractionDigits="2"/>
						</td>
						<td><joda:format value="${auction.winningBid.bidTime}" style="MM" /></td>
						<td>
							<c:out value="${auction.winningBid.bidder.firstName}"/>
							<c:out value="${auction.winningBid.bidder.lastName}"/>
						</td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</tbody>
</table>
<!-- // running.jsp END -->