<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<spring:message code="form.select.label" var="label"/>
<script type="text/javascript">
$(document).ready(function() {
	function updateCities() {
		console.log("Loading cities using Ajax...");
		$("#city").empty().append($("<option />").val("").text("${label}"));
	    var selectVal = $("#state").val();
	    if (selectVal == "") return;
	    $("#state").attr("disabled", "disabled");
	    $("#city").attr("disabled", "disabled");
	    $.get("<spring:url value="/util/cities"/>", { state: selectVal }, function(data) {
	    	$.each(data, function() {
	    		$("#city").append($("<option />").val(this).text(this));
	    	});
	    	$("#city").removeAttr("disabled");
		    $("#state").removeAttr("disabled");
	    });
	}
	$("#state").change(updateCities);
});
</script>

<form:form modelAttribute="userForm">
<div class="wizard">
	<div class="widget widget-tabs widget-tabs-double">
	
		<!-- Widget heading -->
		<div class="widget-head">
			<ul>
				<li class="active"><a href="#info" class="glyphicons notes" data-toggle="tab"><i></i><span class="strong">Info</span></a></li>
				<li><a href="#contact" class="glyphicons phone" data-toggle="tab"><i></i><span class="strong">Contact</span></a></li>
			</ul>
		</div>
		<!-- // Widget heading END -->
		
		<div class="widget-body">
			
			<form:errors path="*" element="div" cssClass="alert alert-error"/>
			
			<div class="tab-content">
			
				<div class="tab-pane active" id="info">
					<div class="row-fluid">
						<div class="span6">
							
							<spring:bind path="username">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="username" cssClass="control-label"><spring:message code="login.username"/></form:label>
								</div>
								<div class="controls">
									<form:input path="username" readonly="${userForm.existing}"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="username"/></p>
									</c:if>
								</div>
							</spring:bind>
						
							<spring:bind path="firstName">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="firstName" cssClass="control-label"><spring:message code="user.firstname"/></form:label>
								</div>
								<div class="controls">
									<form:input path="firstName"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="firstName"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="lastName">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="lastName" cssClass="control-label"><spring:message code="user.lastname"/></form:label>
								</div>
								<div class="controls">
									<form:input path="lastName"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="lastName"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<div class="control-group">
								<form:label path="role" cssClass="control-label"><spring:message code="user.role"/></form:label>
							</div>
							<div class="controls">
								<form:select path="role" items="${roles}"/>
							</div>
							
							<c:if test="${userForm.existing}">
								<div class="controls">
									<form:label path="enabled" cssClass="checkbox uniformjs">
										<form:checkbox path="enabled" cssClass="checkbox"/><spring:message code="user.enabled"/>
									</form:label>
								</div>
							</c:if>
						</div>
						
						<div class="span6">
							<spring:bind path="pan">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="pan" cssClass="control-label"><spring:message code="user.pan"/></form:label>
								</div>
								<div class="controls">
									<form:input path="pan"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="pan"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="vat">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="vat" cssClass="control-label"><spring:message code="user.vat"/></form:label>
								</div>
								<div class="controls">
									<form:input path="vat"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="vat"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="cst">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="cst" cssClass="control-label"><spring:message code="user.cst"/></form:label>
								</div>
								<div class="controls">
									<form:input path="cst"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="cst"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="ecc">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="ecc" cssClass="control-label"><spring:message code="user.ecc"/></form:label>
								</div>
								<div class="controls">
									<form:input path="ecc"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="ecc"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="bankDetails">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="bankDetails" cssClass="control-label"><spring:message code="user.bankDetails"/></form:label>
								</div>
								<div class="controls">
									<form:input path="bankDetails"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="bankDetails"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="userType">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="userType" cssClass="control-label"><spring:message code="user.userType"/></form:label>
								</div>
								<div class="controls">
									<form:select path="userType" items="${userTypes}"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="userType"/></p>
									</c:if>
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
				
				<div class="tab-pane" id="contact">
					<div class="row-fluid">
						<div class="span6">
							
							<spring:bind path="organization">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="organization" cssClass="control-label"><spring:message code="user.organization"/></form:label>
								</div>
								<div class="controls">
									<form:input path="organization"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="organization"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="contactPersonName">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="contactPersonName" cssClass="control-label"><spring:message code="user.contactPersonName"/></form:label>
								</div>
								<div class="controls">
									<form:input path="contactPersonName"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="contactPersonName"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="contactPersonDesignation">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="contactPersonDesignation" cssClass="control-label"><spring:message code="user.contactPersonDesignation"/></form:label>
								</div>
								<div class="controls">
									<form:input path="contactPersonDesignation"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="contactPersonDesignation"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="contactNumber">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="contactNumber" cssClass="control-label"><spring:message code="user.contactNumber"/></form:label>
								</div>
								<div class="controls">
									<form:input path="contactNumber"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="contactNumber"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="fax">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="fax" cssClass="control-label"><spring:message code="user.fax"/></form:label>
								</div>
								<div class="controls">
									<form:input path="fax"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="fax"/></p>
									</c:if>
								</div>
							</spring:bind>
							
						</div>
						
						<div class="span6">
							
							<spring:bind path="address">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="address" cssClass="control-label"><spring:message code="user.address"/></form:label>
								</div>
								<div class="controls">
									<form:input path="address"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="address"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="state">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="state" cssClass="control-label"><spring:message code="user.state"/></form:label>
								</div>
								<div class="controls">
									<form:select path="state" items="${states}"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="state"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="city">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="state" cssClass="control-label"><spring:message code="user.city"/></form:label>
								</div>
								<div class="controls">
									<form:select path="city" items="${states}"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="city"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="pincode">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="pincode" cssClass="control-label"><spring:message code="user.pincode"/></form:label>
								</div>
								<div class="controls">
									<form:input path="pincode"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="pincode"/></p>
									</c:if>
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
				
			</div>
			
			<div class="right form-actions">
				<button type="submit" class="btn btn-primary btn-icon glyphicons circle_ok"><i></i><spring:message code="common.save"/></button>
				<a href="javascript: window.location.href=auctionForm.action;" class="btn btn-default btn-icon glyphicons circle_remove"><i></i><spring:message code="common.cancel"/></a>
			</div>
			
		</div>
	</div>
</div>
</form:form>
