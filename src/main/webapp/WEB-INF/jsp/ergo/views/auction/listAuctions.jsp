<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<div class="widget">
	<div class="widget-head">
		<h4 class="heading"><spring:message code="auction.list.heading"/></h4>
	</div>
	<div class="widget-body">
		<c:choose>
			<c:when test="${empty auctions}">
				<spring:message code="auction.notfound" />
			</c:when>
			<c:otherwise>
				<table class="dynamicTable table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><spring:message code="auction.auctionCode"/></th>
							<th><spring:message code="auction.name"/></th>
							<th><spring:message code="auction.startdate"/></th>
							<th><spring:message code="auction.enddate"/></th>
							<th class="span1"/>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${auctions}" var="auction">
						<spring:url value="/auction/{auctionCode}" var="editUrl">
							<spring:param name="auctionCode" value="${auction.auctionCode}"/>
						</spring:url>
						<tr>
							<td><a href="${fn:escapeXml(editUrl)}"><c:out value="${auction.auctionCode}"/></a></td>
							<td><c:out value="${auction.name}"/></td>
							<td><joda:format value="${auction.startDate}" style="MM" /></td>
							<td><joda:format value="${auction.endDate}" style="MM" /></td>
							<td><a class="btn btn-block btn-icon glyphicons pencil" href="${fn:escapeXml(editUrl)}"><i></i><spring:message code="form.button.edit"/></a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>