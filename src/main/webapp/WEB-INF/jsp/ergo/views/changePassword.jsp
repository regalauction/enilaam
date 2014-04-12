<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form modelAttribute="changePassword">
<div class="row-fluid">
	<div class="span3">
		<strong><spring:message code="userForm.changepassword.heading"/></strong>
		<p class="muted"><spring:message code="userForm.changepassword.description"/></p>
	</div>
	<div class="span9">
		<spring:bind path="oldPassword">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="oldPassword" cssClass="control-label">
					<spring:message code="userForm.oldpassword"/>
				</form:label>
			</div>
			<div class="controls">
				<form:password path="oldPassword" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="oldPassword"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="newPassword">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="newPassword" cssClass="control-label">
					<spring:message code="userForm.newpassword"/>
				</form:label>
			</div>
			<div class="controls">
				<form:password path="newPassword" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="newPassword"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="retypePassword">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="retypePassword" cssClass="control-label">
					<spring:message code="userForm.retypepassword"/>
				</form:label>
			</div>
			<div class="controls">
				<form:password path="retypePassword" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="retypePassword"/></p></c:if>
			</div>
		</spring:bind>
		<div class="form-actions">
			<input class="btn btn-primary" type="submit" value="<spring:message code="userForm.changePassword.submit"/>"/>
			<button type="button" class="btn" data-dismiss="modal"><spring:message code="common.cancel"/></button>
		</div>
	</div>
</div>
</form:form>
