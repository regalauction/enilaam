<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form modelAttribute="registrationForm">

<div class="row-fluid">
	<div class="span6">
		<strong><spring:message code="login.register"/></strong>
		<p class="muted"><spring:message code="register.description"/></p>
	</div>
	<div class="span6">
		<spring:bind path="email">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="email" cssClass="control-label"><spring:message code="register.email"/></form:label>
			</div>
			<div class="controls">
				<form:input path="email" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="email"/></p></c:if>
			</div>
		</spring:bind>
		
		<div class="form-actions">
			<input class="btn btn-primary" type="submit" value="<spring:message code="register.submit"/>"/>
			<button type="button" class="btn" data-dismiss="modal"><spring:message code="common.cancel"/></button>
		</div>
	</div>
</div>

</form:form>