<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- Profile / Logout menu -->
<li class="account dropdown dd-1">
	<a data-toggle="dropdown" href="#" class="glyphicons logout lock" title="<spring:message code="menu.account.loggedinas" arguments="${userDisplayName}"/>">
		<span class="hidden-phone">
			<c:choose>
				<c:when test="${not empty user.firstName}"><c:out value="${user.firstName}"/></c:when>
				<c:when test="${not empty user.lastName}"><c:out value="${user.lastName}"/></c:when>
				<c:otherwise><c:out value="${user.username}"/></c:otherwise>
			</c:choose>
		</span><i></i>
	</a>
	<ul class="dropdown-menu pull-right">
		<li>
			<a data-toggle="modal" data-target="#changePasswordModal" data-backdrop="static"
				href="<spring:url value="/profile/changePassword" />" class="glyphicons keys">
				<spring:message code="menu.user.changepassword"/><i></i>
			</a>
		</li>
		<li class="profile">
			<span>
				<a data-toggle="modal" data-target="#profileDetailsModal" href="javascript: void(0);">
					<span class="heading"><spring:message code="menu.account.profile"/></span>
					<span class="img"></span>
					<span class="details">
						<c:out value="${userDisplayName}" escapeXml="false"/>
						<c:out value="${user.username}"/>
					</span>
				</a>
			<span class="clearfix"></span>
			</span>
		</li>
		<li>
			<span>
				<a class="btn btn-default btn-mini pull-right" href="<spring:url value="/j_spring_security_logout" />"><spring:message code="logout" /></a>
			</span>
		</li>
	</ul>
</li>
<!-- // Profile / Logout menu END -->


<!-- Change Password Modal Holder -->
<div class="modal hide fade" id="changePasswordModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
			</div>
		</div>
	</div>
</div>
<!-- // Change Password Modal Holder END -->

<!-- Profile Details -->
<div class="modal hide fade" id="profileDetailsModal">
	<div class="modal-dialog">
		<div class="modal-content">
			  <div class="modal-header">
		      	<button type="button" class="close" data-dismiss="modal">&times;</button>
		      	<h4 class="modal-title glyphicons user"><i></i><spring:message code="menu.account.profile"/></h4>
		      </div>
			<div class="modal-body">
				<dl class="dl-horizontal">
				<c:if test="${not empty user.firstName || not empty user.lastName}">
					<dt><spring:message code="user.name"/></dt>
					<dd><c:out value="${user.firstName}"/> <c:out value="${user.lastName}"/></dd>
				</c:if>
				
				<c:if test="${not empty user.organization}">
					<dt><spring:message code="user.organization"/></dt>
					<dd><c:out value="${user.organization}"/></dd>
				</c:if>
				
					<dt><spring:message code="user.mail"/></dt>
					<dd><c:out value="${user.username}"/></dd>
					
				<c:if test="${not empty user.contactPersonName}">
					<dt><spring:message code="user.contactPersonName"/></dt>
					<dd><c:out value="${user.contactPersonName}"/></dd>
				</c:if>
					
				<c:if test="${not empty user.contactNumber}">
					<dt><spring:message code="user.contactNumber"/></dt>
					<dd><c:out value="${user.contactNumber}"/></dd>
				</c:if>
				
				<c:if test="${not empty user.fax}">
					<dt><spring:message code="user.fax"/></dt>
					<dd><c:out value="${user.fax}"/></dd>
				</c:if>
				
				<c:set var="address" value="${user.address}"/>
				<c:if test="${not empty address.address 
							or not empty address.city 
							or not empty address.state 
							or not empty address.pincode}">
					<dt><spring:message code="user.address"/></dt>
					<dd>
						<c:if test="${not empty address.address}"><c:out value="${address.address}"/>,</c:if>
						<c:if test="${not empty address.city}"><c:out value="${address.city}"/>,</c:if>
						<c:if test="${not empty address.state}"><c:out value="${address.state}"/>,</c:if>
						<c:if test="${not empty address.pincode}"><spring:message code="user.pincode"/>: <c:out value="${address.pincode}"/></c:if>
					</dd>
				</c:if>
				</dl>
			</div>
			<div class="modal-footer">
	        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	     	</div>
		</div>
	</div>
</div>
<!-- // Profile Details END-->