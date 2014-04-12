<!-- history.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="widget">
	<div class="widget-head">
		<h4 class="heading glyphicons history"><i></i><spring:message code="auction.history"/></h4>
	</div>
	<div class="widget-body">
		<c:choose>
			<c:when test="${empty auctions}">
				No auction history.
			</c:when>
			<c:otherwise>
				<table class="dynamicTable table table-condensed">
					<thead>
						<tr>
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
									<c:out value="${auction.name}"/>
									<c:if test="${not empty auction.documents}">
										<ul>
											<c:forEach items="${auction.documents}" var="document">
												<li><a href="<spring:url value="/attachments/documents/${document.code}"/>"><c:out value="${document.name}"/></a></li>
											</c:forEach>
										</ul>
									</c:if>
								</td>
								<td><spring:message code="auction.type.${auction.auctionType}"/></td>
								<td><joda:format value="${auction.startDate}" style="MM" /></td>
								<td><joda:format value="${auction.endDate}" style="MM" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<!-- // history.jsp END -->