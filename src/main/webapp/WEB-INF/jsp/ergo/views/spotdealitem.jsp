<!-- spotdealitem.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

<form:form modelAttribute="spotDealItemForm">
<div class="wizard">
	<div class="widget widget-tabs widget-tabs-double">
	
		<!-- Widget heading -->
		<div class="widget-head">
			<ul>
				<li class="active"><a href="#info" class="glyphicons notes" data-toggle="tab"><i></i><span class="strong">Info</span></a></li>
				<li><a href="#images" class="glyphicons camera" data-toggle="tab"><i></i><span class="strong">Images</span>
					<span>(${not empty spotDealItemForm.currImages? fn:length(spotDealItemForm.currImages) : 'empty'})</span>
				</a></li>
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
									<form:label path="name" cssClass="control-label"><spring:message code="spotdeal.name"/></form:label>
								</div>
								<div class="controls">
									<form:textarea path="name"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="name"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="spec">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="spec" cssClass="control-label"><spring:message code="spotdeal.spec"/></form:label>
								</div>
								<div class="controls">
									<form:textarea path="spec"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="spec"/></p>
									</c:if>
								</div>
							</spring:bind>				
							
							
							<spring:bind path="code">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="code" cssClass="control-label"><spring:message code="spotdeal.code"/></form:label>
								</div>
								<div class="controls">
									<form:input path="code" readonly="${spotDealItemForm.existing}"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="code"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="unitOfMeasure">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="unitOfMeasure" cssClass="control-label"><spring:message code="spotdeal.unitOfMeasure"/></form:label>
								</div>
								<div class="controls">
									<form:input path="unitOfMeasure" />
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="unitOfMeasure"/></p>
									</c:if>
								</div>
							</spring:bind>		
												
							<spring:bind path="quantity">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="quantity" cssClass="control-label"><spring:message code="spotdeal.quantity"/></form:label>
								</div>
								<div class="controls">
									<form:input path="quantity" />
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="quantity"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="basePrice">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="basePrice" cssClass="control-label"><spring:message code="spotdeal.baseprice"/></form:label>
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
							
							<spring:bind path="enabled">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="enabled" cssClass="control-label">
										<form:checkbox path="enabled"/>
										<spring:message code="spotdeal.enabled"/>
									</form:label>
								</div>
								<div class="controls">
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="enabled"/></p>
									</c:if>
								</div>
							</spring:bind>
						</div>
						<div class="span6">
						
							<spring:bind path="sellerName">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="sellerName" cssClass="control-label"><spring:message code="spotdeal.sellerName"/></form:label>
								</div>
								<div class="controls">
									<form:input path="sellerName" />
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="sellerName"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="sellerEmail">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="sellerEmail" cssClass="control-label"><spring:message code="spotdeal.sellerEmail"/></form:label>
								</div>
								<div class="controls">
									<form:input path="sellerEmail" />
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="sellerEmail"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="deliveryPeriod">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="deliveryPeriod" cssClass="control-label"><spring:message code="spotdeal.deliveryPeriod"/></form:label>
								</div>
								<div class="controls">
									<form:input path="deliveryPeriod" />
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="deliveryPeriod"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							
							<spring:bind path="address">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="address" cssClass="control-label"><spring:message code="spotdeal.address"/></form:label>
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
									<form:label path="state" cssClass="control-label"><spring:message code="spotdeal.state"/></form:label>
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
									<form:label path="state" cssClass="control-label"><spring:message code="spotdeal.city"/></form:label>
								</div>
								<div class="controls">
									<form:select path="city" items="${cities}"/>
									<c:if test="${status.error}">
										<p class="error help-block"><form:errors cssClass="label label-important" path="city"/></p>
									</c:if>
								</div>
							</spring:bind>
							
							<spring:bind path="pincode">
								<div class="control-group ${status.error ? 'error' : ''}">
									<form:label path="pincode" cssClass="control-label"><spring:message code="spotdeal.pincode"/></form:label>
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
					
					
					<div class="tab-pane" id="images">
					<div class="row-fluid">
						<c:if test="${not empty spotDealItemForm.currImages}">
							<ul class="thumbnails">
								<c:forEach var="image" items="${spotDealItemForm.currImages}">
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
								
				
			</div>
			
			<div class="right form-actions">
				<button type="submit" class="btn btn-primary btn-icon glyphicons circle_ok"><i></i><spring:message code="common.save"/></button>
				<a href="javascript: window.location.href=spotDealItemForm.action;" class="btn btn-default btn-icon glyphicons circle_remove"><i></i><spring:message code="common.cancel"/></a>
			</div>
		</div>
	</div>
</div>
</form:form>
<!-- // spotdealitem.jsp END -->