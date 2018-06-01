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
		
		<spring:bind path="pan">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="pan" cssClass="control-label"><spring:message code="register.pan"/></form:label>
			</div>
			<div class="controls">
				<form:input path="pan" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="pan"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="cst">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="cst" cssClass="control-label"><spring:message code="register.cst"/></form:label>
			</div>
			<div class="controls">
				<form:input path="cst" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="cst"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="vat">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="vat" cssClass="control-label"><spring:message code="register.vat"/></form:label>
			</div>
			<div class="controls">
				<form:input path="vat" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="vat"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="field1">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="field1" cssClass="control-label"><spring:message code="register.field1"/></form:label>
			</div>
			<div class="controls">
				<form:input path="field1" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="field1"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="field2">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="field2" cssClass="control-label"><spring:message code="register.field2"/></form:label>
			</div>
			<div class="controls">
				<form:input path="field2" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="field2"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="banker">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="banker" cssClass="control-label"><spring:message code="register.banker"/></form:label>
			</div>
			<div class="controls">
				<form:input path="banker" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="banker"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="accountNumber">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="accountNumber" cssClass="control-label"><spring:message code="register.accountNumber"/></form:label>
			</div>
			<div class="controls">
				<form:input path="accountNumber" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="accountNumber"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="branch">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="branch" cssClass="control-label"><spring:message code="register.branch"/></form:label>
			</div>
			<div class="controls">
				<form:input path="branch" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="branch"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="branchCode">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="branchCode" cssClass="control-label"><spring:message code="register.branchCode"/></form:label>
			</div>
			<div class="controls">
				<form:input path="branchCode" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="branchCode"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="micrCode">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="micrCode" cssClass="control-label"><spring:message code="register.micrCode"/></form:label>
			</div>
			<div class="controls">
				<form:input path="micrCode" cssClass="span10"/>
				<c:if test="${status.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="micrCode"/></p></c:if>
			</div>
		</spring:bind>
		
		<spring:bind path="ifscCode">
			<div class="control-group ${status.error ? 'error' : ''}">
				<form:label path="ifscCode" cssClass="control-label"><spring:message code="register.ifscCode"/></form:label>
			</div>
			<div class="controls">
				<form:input path="cst" cssClass="span10"/>
				<c:if test="${ifscCode.error}"><p class="error help-block"><form:errors cssClass="label label-important" path="ifscCode"/></p></c:if>
			</div>
		</spring:bind>	
		
		
		<div class="form-actions">
			<input class="btn btn-primary" type="submit" value="<spring:message code="register.submit"/>"/>
			<button type="button" class="btn" data-dismiss="modal"><spring:message code="common.cancel"/></button>
		</div>
	</div>
</div>

</form:form>