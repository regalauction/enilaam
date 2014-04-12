<!-- item.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form modelAttribute="itemForm">
<div class="wizard">
	<div class="widget widget-tabs widget-tabs-double">
	
		<!-- Widget heading -->
		<div class="widget-head">
			<ul>
				<li class="active"><a href="#info" class="glyphicons notes" data-toggle="tab"><i></i><span>Info</span></a></li>
				<li><a href="#images" class="glyphicons camera" data-toggle="tab"><i></i><span>Images</span></a></li>
			</ul>
		</div>
		<!-- // Widget heading END -->
		
		<div class="widget-body">
		
			<form:errors path="*" element="div" cssClass="alert alert-error"/>
			
			<div class="tab-content">
			
				<div class="tab-pane active" id="info">
					<spring:bind path="name">
						<div class="control-group ${status.error ? 'error' : ''}">
							<form:label path="name" cssClass="control-label"><spring:message code="item.name"/></form:label>
						</div>
						<div class="controls">
							<form:textarea path="name"/>
							<c:if test="${status.error}">
								<p class="error help-block"><form:errors cssClass="label label-important" path="name"/></p>
							</c:if>
						</div>
					</spring:bind>
					
					<spring:bind path="code">
						<div class="control-group ${status.error ? 'error' : ''}">
							<form:label path="code" cssClass="control-label"><spring:message code="item.code"/></form:label>
						</div>
						<div class="controls">
							<form:input path="code" readonly="${itemForm.existing}"/>
							<c:if test="${status.error}">
								<p class="error help-block"><form:errors cssClass="label label-important" path="code"/></p>
							</c:if>
						</div>
					</spring:bind>
					
				</div>
				
				<div class="tab-pane" id="images">
					<div class="row-fluid">
						<c:if test="${not empty itemForm.currImages}">
							<ul class="thumbnails">
								<c:forEach var="image" items="${itemForm.currImages}">
									<spring:url value="/attachments/images/{filename}" var="imageUrl">
										<spring:param name="filename" value="${image.code}"/>
									</spring:url>
									<li>
										<div class="thumbnail">
											<div class="img">
												<div><a href="${imageUrl}"><img alt="${image.name}" src="${imageUrl}" height="153" width="180"/></a></div>
											</div>
											<div class="caption">
												<h5 class="filename"><a href="${imageUrl}"><c:out value="${image.name}"/></a></h5>
												<div class="controls text-center">
													<div class="delete">
														<label class="checkbox uniformjs">
															<form:checkbox path="deleteFiles" value="${image.code}" cssClass="checkbox deleteFiles"/>
															<spring:message code="common.delete"/>
														</label>
													</div>
												</div>
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>
							
							<hr class="separator"/>
						</c:if>
						<em><spring:message code="uploader.newuploads"/></em>
						
						<ul id="imageuploader_filelist" class="thumbnails">
							<li id="imageuploader_uploaditem" class="hide">
								<div class="thumbnail">
									<div class="img"></div>
									<div class="caption">
										<h5 class="filename"></h5>
										<div class="controls text-center">
											<form:hidden path="addFiles" cssClass="addFiles"/>
											<div class="filesize"></div>
											<div class="progress">
												<div class="progress progress-primary progress-mini">
													<div class="bar"></div>
												</div>
											</div>
											<div class="delete">
												<label class="checkbox">
													<form:checkbox path="deleteFiles" value="-" cssClass="deleteFiles"/>
													<spring:message code="common.delete"/>
												</label>
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
					
					<spring:url value="/attachments/upload/{attachmentType}" var="uploadUrl">
						<spring:param name="attachmentType" value="${attachmentType}"/>
					</spring:url>
					
					<div id="imageuploader_container"
						data-extensions="<spring:message code="uploader.image.extensions"/>" 
						data-url="${uploadUrl}"
						data-attachmentUrlPrefix="<spring:url value="/attachments/images/"/>">
						<a id="imageuploader_pickfiles" class="btn btn-default"><spring:message code="uploader.browse"/></a>
						<a id="imageuploader_uploadfiles" class="btn btn-default btn-icon glyphicons upload"><i></i><spring:message code="uploader.upload"/></a>
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
<!-- // item.jsp END -->