<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<script type="text/javascript">
<!--
$(document).ready(function(){
	  $("#bidders").dataTable();
	  $("#observers").dataTable();
	});
//-->
</script>
<form:form modelAttribute="auctionForm" enctype="multipart/form-data">
	<spring:hasBindErrors name="auctionForm">
		<div class="error"><spring:message code="form.validation.errors"/></div>
	</spring:hasBindErrors>
	<div class="span-12">
		<fieldset>
			<legend>Basic</legend>
			
			<custom:input path="name" labelCode="auction.name" required="true" cssClass="x-wide"/>
			<custom:select items="${items}" path="item" itemValue="code" itemLabel="name" labelCode="item.name" required="true" cssClass="wide"/>
			<custom:input path="startDate" labelCode="auction.startdate"/>
			<custom:input path="endDate" labelCode="auction.enddate"/>
			<custom:label labelCode="auction.type" forPath="auctionType"/>
			<form:select path="auctionType" cssClass="wide">
				<c:forEach items="${auctionTypes}" var="auctionType">
					<spring:message code="auction.type.${auctionType}" var="auctionTypeName"/>
					<form:option value="${auctionType}" label="${auctionTypeName}"/>
				</c:forEach>
			</form:select>
		</fieldset>
		<fieldset>
			<legend>Price Details</legend>
			
			<custom:input path="basePrice" labelCode="auction.baseprice" required="true"/>
			<custom:input path="reservePrice" labelCode="auction.reserveprice" required="true"/>
			<custom:input path="deltaPrice" labelCode="auction.deltaprice" required="true"/>
			<custom:input path="timeExtension" labelCode="auction.timeextension" cssClass="thin" required="true"/>
		</fieldset>
		<fieldset>
			<legend>Attachments</legend>
			
			<form:checkboxes items="${auctionForm.currDocuments}" path="updateDocuments" itemLabel="name" itemValue="code" delimiter="<br/>"/><br />
			
			<input type="file" name="documents[0]"/>
			<input type="file" name="documents[1]"/>
		</fieldset>
	</div>
	<div class="span-12 last">
		<fieldset>
			<legend>Attached Bidders</legend>
			<table id="bidders" class="no-zebra">
				<thead>
					<tr>
						<th />
						<th><spring:message code="login.username"/></th>
						<th><spring:message code="user.firstname"/></th>
						<th><spring:message code="user.lastname"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bidders}" var="user">
						<tr>
							<td><form:checkbox path="bidders" value="${user.username}"/></td>
							<td><c:out value="${user.username}"/></td>
							<td><c:out value="${user.firstName}"/></td>
							<td><c:out value="${user.lastName}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		<fieldset>
			<legend>Attached Observers</legend>
			<table id="observers" class="no-zebra">
				<thead>
					<tr>
						<th />
						<th><spring:message code="login.username"/></th>
						<th><spring:message code="user.firstname"/></th>
						<th><spring:message code="user.lastname"/></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${observers}" var="user">
						<tr>
							<td><form:checkbox path="bidders" value="${user.username}"/></td>
							<td><c:out value="${user.username}"/></td>
							<td><c:out value="${user.firstName}"/></td>
							<td><c:out value="${user.lastName}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</div>
	<div class="span-24"><custom:button type="submit" labelCode="auction.submit"/></div>
</form:form>