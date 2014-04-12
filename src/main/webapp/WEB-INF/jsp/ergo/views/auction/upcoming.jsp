<!-- upcoming.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="/WEB-INF/tlds/util.tld" prefix="util"%>
<div class="widget">
	<div class="widget-head">
		<h4 class="heading glyphicons binoculars"><i></i><spring:message code="auction.upcoming"/></h4>
	</div>
	<div class="widget-body">
		<c:choose>
			<c:when test="${empty auctions}">
				No upcoming auctions.
			</c:when>
			<c:otherwise>
				<table id="upcomingAuctionsTable" class="table table-condensed">
					<thead>
						<tr>
							<th><spring:message code="common.auction" /></th>
							<th><spring:message code="common.type" /></th>
							<th><spring:message code="common.date" /></th>
							<th><spring:message code="common.duration" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${auctions}" var="auction">
							<tr>
								<td>
									<c:out value="${auction.name}"/>
									<c:if test="${not empty auction.documents}">
										<ul>
											<c:forEach items="${auction.documents}" var="document">
												<li><a href="<spring:url value="/attachments/documents/${document.code}"/>"><c:out value="${document.name}"/></a></li>
											</c:forEach>
										</ul>
									</c:if>
								</td>
								<td><spring:message code="auction.type.${auction.auctionType}.short"/></td>
								<td><joda:format value="${auction.startDate}" style="MS" /></td>
								<td>2 Hours</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<!-- // upcoming.jsp END -->