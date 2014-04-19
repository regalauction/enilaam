<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="widget">
	<div class="widget-head">
		<h4 class="heading"><spring:message code="item.list.heading"/></h4>
	</div>
	<div class="widget-body">
		<c:choose>
			<c:when test="${empty items}">
				<spring:message code="item.notfound" />
			</c:when>
			<c:otherwise>
				<table class="dynamicTable table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th><spring:message code="item.code"/></th>
							<th><spring:message code="item.name"/></th>
							<%-- <th><spring:message code="item.thumbnail"/></th> --%>
							<th class="span1"/>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${items}" var="item">
						<spring:url value="/item/{code}" var="editUrl">
							<spring:param name="code" value="${item.code}"/>
						</spring:url>
						<tr>
							<td><a href="${fn:escapeXml(editUrl)}"><c:out value="${item.code}"/></a></td>
							<td><c:out value="${item.name}"/></td>
							<%-- <td>
								<c:if test="${not empty item.thumbnail}">
									<img alt="<spring:message code="thumbnail.alt" arguments="${item.name}"/>" 
									src="<spring:url value="/attachments/images/${item.thumbnail.code}" />"
									height="<spring:theme code="item.thumbnail.height" />"
									width="<spring:theme code="item.thumbnail.width" />"/>
								</c:if>
							</td> --%>
							<td><a class="btn btn-block btn-icon glyphicons pencil" href="${fn:escapeXml(editUrl)}"><i></i><spring:message code="form.button.edit"/></a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>