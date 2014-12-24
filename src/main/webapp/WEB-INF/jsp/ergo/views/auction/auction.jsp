<!-- auction.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<form:form modelAttribute="auctionForm">
<div class="wizard">
	<div class="widget widget-tabs widget-tabs-double">
	
		<!-- Widget heading -->
		<div class="widget-head">
			<ul>
				<li class="active"><a href="#info" class="glyphicons notes" data-toggle="tab"><i></i><span class="strong">Info</span></a></li>
				<li>
					<a href="#attachments" class="glyphicons paperclip" data-toggle="tab"><i></i><span class="strong">Attachments</span>
					<span>(${not empty auctionForm.currDocuments? fn:length(auctionForm.currDocuments) : 'empty'})</span></a>
				</li>				
				<li>
					<a href="#bidders" class="glyphicons group" data-toggle="tab"><i></i><span class="strong"><spring:message code="auction.bidders"/></span>
					<span>(${(numBidders gt 0)? numBidders: 'empty'})</span></a>
				</li>
				<li>
					<a href="#observers" class="glyphicons group" data-toggle="tab"><i></i><span class="strong"><spring:message code="auction.observers"/></span>
					<span>(${(numObservers gt 0)? numObservers: 'empty'})</span></a>
				</li>
			</ul>
		</div>
		<!-- // Widget heading END -->
		
		<div class="widget-body">
		
			<form:errors path="*" element="div" cssClass="alert alert-error"/>
			
			
			<div class="tab-content">
			
				<div class="tab-pane active" id="info">
					<div class="row-fluid">
						<div class="span6">
							<spring:bind path="name">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="name" cssClass="control-label"><spring:message code="auction.name"/></form:label>
								</div>
								<div class="controls">
									<form:textarea path="name" cssClass="input-xlarge"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="name"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="item">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="item" cssClass="control-label"><spring:message code="item.name"/></form:label>
								</div>
								<div class="controls">
									<form:select path="item" items="${items}" itemLabel="name" itemValue="code"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="item"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<div class="control-group">
								<form:label path="auctionType" cssClass="control-label"><spring:message code="auction.type"/></form:label>
							</div>
							<div class="controls">
								<form:select path="auctionType">
									<c:forEach items="${auctionTypes}" var="auctionType">
										<spring:message code="auction.type.${auctionType}" var="auctionTypeName"/>
										<form:option value="${auctionType}" label="${auctionTypeName}"/>
									</c:forEach>
								</form:select>
							</div>
							
							<spring:bind path="startDate">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="startDate" cssClass="control-label"><spring:message code="auction.startdate"/></form:label>
								</div>
								<div class="controls">
									<div class="input-append date datetimepicker">
									    <form:input path="startDate"/>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="startDate"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="endDate">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="endDate" cssClass="control-label"><spring:message code="auction.enddate"/></form:label>
								</div>
								<div class="controls">
									<div class="input-append date datetimepicker">
									    <form:input path="endDate"/>
									    <span class="add-on"><i class="icon-th"></i></span>
									</div>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="endDate"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							
						</div>
						<div class="span6">
							
							<spring:bind path="basePrice">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="basePrice" cssClass="control-label"><spring:message code="auction.baseprice"/></form:label>
								</div>
								<div class="controls">
									<div class="input-prepend">
									  	<span class="add-on"><spring:message code="common.currencySymbol"/></span>
										<form:input path="basePrice" cssClass="input-small right"/>
									</div>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="basePrice"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							
							<spring:bind path="reservePrice">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="reservePrice" cssClass="control-label"><spring:message code="auction.reserveprice"/></form:label>
								</div>
								<div class="controls">
									<div class="input-prepend">
									  	<span class="add-on"><spring:message code="common.currencySymbol"/></span>
										<form:input path="reservePrice" cssClass="input-small right"/>
									</div>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="reservePrice"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							
							<spring:bind path="deltaPrice">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="reservePrice" cssClass="control-label"><spring:message code="auction.deltaprice"/></form:label>
								</div>
								<div class="controls">
									<div class="input-prepend">
									  	<span class="add-on"><spring:message code="common.currencySymbol"/></span>
										<form:input path="deltaPrice" cssClass="input-small right"/>
									</div>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="deltaPrice"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<div class="separator"></div>
							
							<spring:bind path="timeExtension">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="timeExtension" cssClass="control-label"><spring:message code="auction.timeextension"/></form:label>
								</div>
								<div class="controls">
									<form:input path="timeExtension" cssClass="input-mini right"/>
									<span style="padding-left: 1em;"><spring:message code="auction.timeExtensionUnit"/></span>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="timeExtension"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							
						</div>
					</div>
				</div>

				<div class="tab-pane" id="attachments">
					<!-- Widget -->
					<div class="widget">
						
						<!-- Widget heading -->
						<div class="widget-head">
							<h4 class="heading glyphicons file_import"><i></i>Upload Attachments</h4>
						</div>
						<!-- // Widget heading END -->
						
						<div class="widget-body">
							<c:if test="${not empty auctionForm.currDocuments}">
								<div>
									<c:forEach var="document" items="${auctionForm.currDocuments}">
										<div class="row-fluid">
											<div class="col1 span5">
												<spring:url value="/attachments/documents/{filename}" var="documentUrl">
													<spring:param name="filename" value="${document.code}"/>
												</spring:url>
												<a href="${documentUrl}"><c:out value="${document.name}"/></a>
											</div>
											<div class="col2 span3 text-center">
												<div class="delete">
													<label class="checkbox uniformjs">
														<form:checkbox path="deleteFiles" value="${document.code}" cssClass="checkbox deleteFiles"/>
														<spring:message code="common.delete"/>
													</label>
												</div>
											</div>
										</div>
									</c:forEach>
								</div>
								<hr class="separator"/>
							</c:if>
							
							<em><spring:message code="uploader.newuploads"/></em>
							
							<div id="docuploader_filelist">
								<div id="docuploader_uploaditem" class="row-fluid hide">
									<div class="col1 span5"></div>
									<div class="col2 span3 text-center">
										<form:hidden path="addFiles" cssClass="addFiles"/>
										<span class="filesize"></span>
										<div class="progress progress-primary"><div class="bar"></div></div>
										<div class="delete">
											<label class="checkbox">
												<form:checkbox path="deleteFiles" value="-" cssClass="deleteFiles"/>
												<spring:message code="common.delete"/>
											</label>
										</div>	
									</div>
								</div>
							</div>
							
							<spring:url value="/attachments/upload/{attachmentType}" var="uploadUrl">
								<spring:param name="attachmentType" value="${attachmentType}"/>
							</spring:url>
							
							<div id="docuploader_container"
								data-extensions="<spring:message code="uploader.document.extensions"/>" 
								data-url="${uploadUrl}"
								data-attachmentUrlPrefix="<spring:url value="/attachments/documents/"/>">
								<a id="docuploader_pickfiles" class="btn btn-default"><spring:message code="uploader.browse"/></a>
								<a id="docuploader_uploadfiles" class="btn btn-default btn-icon glyphicons upload"><i></i><spring:message code="uploader.upload"/></a>
							</div>
																				
						</div>
					</div>
					<!-- // Widget END -->
				</div>
				
				<div class="tab-pane" id="bidders">
					<table class="dynamicTable table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th />
								<th><spring:message code="login.username"/></th>
								<th><spring:message code="user.firstname"/></th>
								<th><spring:message code="user.lastname"/></th>
								<th><spring:message code="user.organization"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bidders}" var="user">
								<tr>
									<td>
										<label class="checkbox uniformjs">
											<form:checkbox path="bidders" value="${user.username}" cssClass="checkbox"/>
										</label>
									</td>
									<td><c:out value="${user.username}"/></td>
									<td><c:out value="${user.firstName}"/></td>
									<td><c:out value="${user.lastName}"/></td>
									<td><c:out value="${user.organization}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="tab-pane" id="observers">
					<table class="dynamicTable table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th />
								<th><spring:message code="login.username"/></th>
								<th><spring:message code="user.firstname"/></th>
								<th><spring:message code="user.lastname"/></th>
								<th><spring:message code="user.organization"/></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${observers}" var="user">
								<tr>
									<td>
										<label class="checkbox uniformjs">
											<form:checkbox path="bidders" value="${user.username}" cssClass="checkbox"/>
										</label>
									</td>
									<td><c:out value="${user.username}"/></td>
									<td><c:out value="${user.firstName}"/></td>
									<td><c:out value="${user.lastName}"/></td>
									<td><c:out value="${user.organization}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
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
<!-- // auction.jsp END -->