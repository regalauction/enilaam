<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $('#auctions').dataTable();
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
					<th><spring:message code="auction.auctionCode"/></th>
					<th><spring:message code="auction.name"/></th>
					<th><spring:message code="auction.startdate"/></th>
					<th><spring:message code="auction.enddate"/></th>
					<th />
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${auctions}" var="auction">
				<spring:url value="/auction/{auctionCode}/edit" var="editUrl">
					<spring:param name="auctionCode" value="${auction.auctionCode}"/>
				</spring:url>
				<tr>
					<td><a href="${fn:escapeXml(editUrl)}"><c:out value="${auction.auctionCode}"/></a></td>
					<td><c:out value="${auction.name}"/></td>
					<td><joda:format value="${auction.startDate}" style="MM" /></td>
					<td><joda:format value="${auction.endDate}" style="MM" /></td>
					<td><custom:button url="${fn:escapeXml(editUrl)}" labelCode="form.button.edit"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>