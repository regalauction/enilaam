<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<tiles:importAttribute name="type" ignore="true"/>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $('#auctions').dataTable(
		  {
			  <c:if test="${type eq 'login'}">
			  "bFilter": false,
			  "bSort": false,
			  "iDisplayLength": 3,
			  "bLengthChange": false
			  </c:if>
		  }
	);
});
//-->
</script>

<table id="auctions" class="no-zebra">
	<c:if test="${type eq 'login'}">
		<caption><spring:message code="auction.upcomming"/></caption>
	</c:if>
	<thead>
		<tr>
			<th></th>
			<th><spring:message code="auction.name" /></th>
			<th><spring:message code="auction.type" /></th>
			<th><spring:message code="auction.startdate" /></th>
			<th><spring:message code="auction.enddate" /></th>
			<security:authorize ifAnyGranted="ROLE_ADMIN"><th /></security:authorize>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${auctions}" var="auction">
			<spring:url value="/auction/{auctionCode}/edit" var="editUrl">
				<spring:param name="auctionCode" value="${auction.auctionCode}"/>
			</spring:url>
			<tr>
				<td>
					<c:if test="${not empty auction.thumbnail}">
						<img alt="<spring:message code="thumbnail.alt" arguments="${auction.item.name}"/>" 
							src="<spring:url value="/attachments/images/${auction.thumbnail}"/>"
							height="<spring:theme code="item.thumbnail.height" />"
							width="<spring:theme code="item.thumbnail.width" />"/>
					</c:if>
				</td>
				<td>
					<security:authorize ifAnyGranted="ROLE_ADMIN"><a href="${fn:escapeXml(editUrl)}"><c:out value="${auction.name}"/></a></security:authorize>
					<security:authorize ifNotGranted="ROLE_ADMIN"><c:out value="${auction.name}"/></security:authorize>
					<c:if test="${not empty auction.documents}">
						<div>
							<ul>
								<c:forEach items="${auction.documents}" var="document">
									<li><a href="<spring:url value="/attachments/documents/${document.code}"/>"><c:out value="${document.name}"/></a></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</td>
				<td><spring:message code="auction.type.${auction.auctionType}"/></td>
				<td><joda:format value="${auction.startDate}" style="MM" /></td>
				<td><joda:format value="${auction.endDate}" style="MM" /></td>
				<security:authorize ifAnyGranted="ROLE_ADMIN">
					<td><custom:button url="${fn:escapeXml(editUrl)}" labelCode="form.button.edit"/></td>
				</security:authorize>
			</tr>
		</c:forEach>
	</tbody>
</table>
