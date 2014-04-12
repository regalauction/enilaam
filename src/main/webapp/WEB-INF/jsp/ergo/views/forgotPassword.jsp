<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>

<form:form modelAttribute="forgotPassword">
<div class="row-fluid">
	<div class="span6">
		<strong><spring:message code="forgotpassword.heading"/></strong>
		<p class="muted"><spring:message code="forgotpassword.description"/></p>
	</div>
	<div class="span6">
		<spring:bind path="username">
			<div class="control-group ${status.error ? 'error' : ''}">
				<label class="control-label" for="username">
					<spring:message code="login.username"/>
				</label>
			</div>
			<div class="controls">
				<form:input path="username" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="username"/></p></c:if>
			</div>
		</spring:bind>
		
		<div class="form-actions">
			<input class="btn btn-primary" type="submit" value="<spring:message code="forgotPassword.reset"/>"/>
			<button type="button" class="btn" data-dismiss="modal"><spring:message code="common.cancel"/></button>
		</div>
	</div>
</div>
</form:form>
	