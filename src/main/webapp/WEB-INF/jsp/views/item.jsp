<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<form:form modelAttribute="itemForm" enctype="multipart/form-data">
	<div class="span-12">
		<spring:hasBindErrors name="auctionForm">
			<div class="error"><spring:message code="form.validation.errors"/></div>
		</spring:hasBindErrors>
		<fieldset>
			<legend>Item</legend>
			
			<custom:input path="name" labelCode="item.name" required="true"/>
			
			<custom:label forPath="code" labelCode="item.code" required="true"/>
			<c:choose>
				<c:when test="${not empty itemForm.code}">
					<c:out value="${itemForm.code}"/><br />
				</c:when>
				<c:otherwise><custom:input path="code"/></c:otherwise>
			</c:choose>
					
			<div>
				<c:if test="${not empty itemForm.currThumbnailPath}">
					<img alt="<spring:message code="thumbnail.alt" arguments="${itemForm.name}"/>" 
					src="<spring:url value="/attachments/images/${itemForm.currThumbnailPath}" />"
					height="<spring:theme code="item.thumbnail.height" />"
					width="<spring:theme code="item.thumbnail.width" />"/>
				</c:if>
			</div>
			<custom:label forPath="thumbnail" labelCode="item.thumbnail"/>
			<input type="file" id="thumbnail" name="thumbnail" />
		</fieldset>
		<div><custom:button type="submit" labelCode="item.submit"/></div>
	</div>
</form:form>