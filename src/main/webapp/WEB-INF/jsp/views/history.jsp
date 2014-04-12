<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script type="text/javascript">
<!--
$(document).ready(function () {
	$("#auctions").dataTable();
});
//-->
</script>
<c:choose>
	<c:when test="${empty auctions}">
		<spring:message code="auction.notfound" />
	</c:when>
	<c:otherwise>
		<table id="auctions" class="no-zebra">
			<thead>
				<tr>
					<th />
					<th><spring:message code="auction.name" /></th>
					<th><spring:message code="auction.type" /></th>
					<th><spring:message code="auction.startdate" /></th>
					<th><spring:message code="auction.enddate" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${auctions}" var="auction">
					<tr>
						<td>
							<img alt="<spring:message code="thumbnail.alt" arguments="${auction.item.name}"/>" 
								src="<spring:url value="${auction.thumbnail}"/>"
								height="<spring:theme code="item.thumbnail.height" />"
								width="<spring:theme code="item.thumbnail.width" />"/>
						</td>
						<td><c:out value="${auction.name}"/></td>
						<td><spring:message code="auction.type.${auction.auctionType}"/></td>
						<td><joda:format value="${auction.startDate}" style="MM" /></td>
						<td><joda:format value="${auction.endDate}" style="MM" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>
