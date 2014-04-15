<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<div class="widget">
	<div class="widget-head">
		<h4 class="heading"><spring:message code="user.list.heading"/></h4>
	</div>
	<div class="widget-body">
		<c:choose>
			<c:when test="${empty users}">
				<spring:message code="user.notfound" />
			</c:when>
			<c:otherwise>
				<table class="dynamicTable table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><spring:message code="login.username"/></th>
							<th><spring:message code="user.name"/></th>
							<th><spring:message code="user.organization"/></th>
							<th><spring:message code="user.contactNumber"/></th>
							<th />
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
						<spring:url value="/user/edit" var="editUrl">
							<spring:param name="q" value="${user.username}"/>
						</spring:url>
						<tr>
							<td><a href="${fn:escapeXml(editUrl)}"><c:out value="${user.username}"/></a></td>
							<td><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></td>
							<td><c:out value="${user.organization}"/></td>
							<td><c:out value="${user.contactNumber}"></c:out></td>
							
							<td><a class="btn btn-block btn-primary btn-icon glyphicons pencil" href="${fn:escapeXml(editUrl)}"><i></i><spring:message code="form.button.edit"/></a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>

