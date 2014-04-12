<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $('#users').dataTable();
});
//-->
</script>
<c:choose>
	<c:when test="${empty users}">
		<spring:message code="user.notfound" />
	</c:when>
	<c:otherwise>
		<table id="users" class="no-zebra">
			<thead>
				<tr>
					<th><spring:message code="login.username"/></th>
					<th><spring:message code="user.name"/></th>
					<th>Organization</th>
					<th>Contact Person</th>
					<th>Designation</th>
					<th>Contact Number</th>
					<th>Address</th>
					<th />
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${users}" var="user">
				<spring:url value="/user/{username}/edit" var="editUrl">
					<spring:param name="username" value="${user.username}"/>
				</spring:url>
				<tr>
					<td><a href="${fn:escapeXml(editUrl)}"><c:out value="${user.username}"/></a></td>
					<td><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
					<td><c:out value="${user.organization}"/></td>
					<td><c:out value="${user.contactPersonName}"></c:out></td>
					<td><c:out value="${user.contactPersonDesignation}"></c:out></td>
					<td><c:out value="${user.contactNumber}"></c:out></td>
					<td>
						<span style="white-space: nowrap;"><c:out value="${user.address.city}"/></span><br />
						<span style="white-space: nowrap;"><c:out value="${user.address.state}"/></span>
					</td>
					
					<td><custom:button url="${fn:escapeXml(editUrl)}" labelCode="form.button.edit"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>