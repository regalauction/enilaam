<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $('#items').dataTable();
});
//-->
</script>
<c:choose>
	<c:when test="${empty items}">
		<spring:message code="item.notfound" />
	</c:when>
	<c:otherwise>
		<table id="items" class="no-zebra">
			<thead>
				<tr>
					<th><spring:message code="item.code"/></th>
					<th><spring:message code="item.name"/></th>
					<th><spring:message code="item.thumbnail"/></th>
					<th />
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${items}" var="item">
				<spring:url value="/item/{code}/edit" var="editUrl">
					<spring:param name="code" value="${item.code}"/>
				</spring:url>
				<tr>
					<td><a href="${fn:escapeXml(editUrl)}"><c:out value="${item.code}"/></a></td>
					<td><c:out value="${item.name}"/></td>
					<td>
						<c:if test="${not empty item.thumbnail}">
							<img alt="<spring:message code="thumbnail.alt" arguments="${item.name}"/>" 
							src="<spring:url value="/attachments/images/${item.thumbnail.code}" />"
							height="<spring:theme code="item.thumbnail.height" />"
							width="<spring:theme code="item.thumbnail.width" />"/>
						</c:if>
					</td>
					<td><custom:button url="${fn:escapeXml(editUrl)}" labelCode="form.button.edit"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>