<!-- running.jsp -->
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<table class="table table-striped">
	<thead>
		<tr>
			<th><spring:message code="auction.auctionCode"/></th>
			<th><spring:message code="auction.name"/></th>
			<th><spring:message code="auction.reserveprice"/></th>
			<th><spring:message code="auction.bidprice"/></th>
			<th><spring:message code="auction.bidtime"/></th>
			<th><spring:message code="bidder"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${auctions}" var="auction">
			<tr>
				<td><c:out value="${auction.auctionCode}"/></td>
				<td><c:out value="${auction.name}"/></td>
				<td>
					<spring:message code="common.currencySymbol"/>&nbsp;
					<fmt:formatNumber value="${auction.reservePrice.amount}" groupingUsed="true" minFractionDigits="0" maxFractionDigits="2"/>
				</td>
				<c:choose>
					<c:when test="${auction.noBid}">
						<td></td><td></td><td></td>
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