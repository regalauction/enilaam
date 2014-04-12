<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<spring:message code="form.select.label" var="label"/>
<script type="text/javascript">
<!--
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

//-->
</script>
<form:form modelAttribute="userForm">
	<spring:hasBindErrors name="userForm">
		<div class="error"><spring:message code="form.validation.errors"/></div>
	</spring:hasBindErrors>
	<div class="span-12">
		<fieldset>
			<legend><spring:message code="userForm.fieldset.legend.basic"/></legend>
			
			<custom:input labelCode="user.firstname" path="firstName" cssClass="wide"/>
			<custom:input labelCode="user.lastname" path="lastName" cssClass="wide"/>
			<custom:input labelCode="login.username" path="username" noUpdate="${userForm.existing}" required="true"/>
			<custom:select labelCode="user.role" path="role" items="${roles}" required="true"/>
			<c:if test="${userForm.existing}">
				<custom:checkbox labelCode="user.enabled" path="enabled"/>
			</c:if>
		</fieldset>
		<fieldset>
			<legend><spring:message code="userForm.fieldset.legend.details"/></legend>
			
			<custom:input labelCode="user.pan" path="pan"/>
			<custom:input labelCode="user.vat" path="vat"/>
			<custom:input labelCode="user.cst" path="cst"/>
			<custom:input labelCode="user.ecc" path="ecc"/>
			<custom:input labelCode="user.bankDetails" path="bankDetails" cssClass="x-wide"/>
			<custom:select labelCode="user.userType" items="${userTypes}" path="userType"/>
		</fieldset>
	</div>
	<div class="span-12 last">
		<fieldset>
			<legend><spring:message code="userForm.fieldset.legend.contact"/></legend>
			
			<custom:input labelCode="user.organization" path="organization" cssClass="wide"/>
			<custom:input labelCode="user.contactPersonName" path="contactPersonName" cssClass="wide"/>
			<custom:input labelCode="user.contactPersonDesignation" path="contactPersonDesignation"/>
			<custom:input labelCode="user.contactNumber" path="contactNumber"/>
			<custom:input labelCode="user.fax" path="fax"/>
			<custom:input labelCode="user.address" path="address" cssClass="x-wide"/>
			<custom:select labelCode="user.state" path="state" items="${states}" cssClass="wide"/>
			<custom:select labelCode="user.city" path="city" items="${cities}" cssClass="wide"/>
			<custom:input labelCode="user.pincode" path="pincode" cssClass="thin"/>
		</fieldset>
	</div>
	<div class="span-24"><custom:button type="submit" labelCode="userForm.submit"/></div>
</form:form>