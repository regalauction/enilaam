<!-- login.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="/WEB-INF/tlds/util.tld" prefix="util"%>
<div class="banner">
	<div class="container-960">
		<div class="banner-wrapper banner-1">
			<!-- <img alt="Regal Auction" src=""> -->
			<!-- <h3>Regal Auction</h3> -->
			
			<div class="row-fluid">
				<div class="span9">
					<img src="<spring:theme code="image.banner"/>" alt="website banner"/>
					<h2 id="appname"><spring:message code="appname"/></h2>
				</div>
				<div class="span3">
					
					<!-- Login form - start -->
					<div class="separator"></div>
					
					<form id="loginForm" method="post" action="j_spring_security_check">
						
						<c:if test="${param.error eq 'true'}">
							<div id="error" class="hidden"><spring:message code="login.authenticationFailure" /></div>
						</c:if>
						
						<div class="control-group">
							<label for="j_username" class="control-label"><spring:message code="login.username" /></label>
						</div>
						<div class="controls">
							<input type="text" name="j_username" id="j_username" class="input-block-level" 
								placeholder="<spring:message code="login.username.placeholder" />" tabindex="1"/>
						</div> 
						
						<div class="control-group">
							<label for="j_password" class="control-label">
								<spring:message code="login.password" />
								<%-- <a id="forgotPassword" class="password" style="float: right;" href="${forgotPasswordUrl}"> --%>
								<a class="password" style="float: right;" 
									data-toggle="modal" data-target="#forgotPasswordModal" href="<spring:url value="/forgotPassword" />">
								<spring:message code="login.forgotPassword"/></a>
							</label>
						</div>
						<div class="controls">
							<input type="password" name="j_password" id="j_password" class="input-block-level margin-none" 
								placeholder="<spring:message code="login.password.placeholder" />" tabindex="2"/>
						</div>
						
						<button type="submit" class="btn btn-large btn-icon btn-primary glyphicons chevron-right" tabindex="3">
							<i></i><spring:message code="login.submit"/>
						</button>
					</form>
					<!-- Login form - end -->

				</div>
			</div>
			
		</div>
	</div>
	<div class="ribbon-wrapper"><div class="ribbon danger">v<span class="large">2</span></div></div>
</div>

<div class="container-960">
	<div class="row-fluid row-merge">
		<div class="span6">
			<div class="innerAll">
				<%@include file="auction/upcoming.jsp" %>
			</div>
		</div>
		<div class="span6">
			<div id="info_tabs" class="widget widget-tabs">
				<div class="widget-body large">
					<div class="tab-content">
						<div id="about_us" class="tab-pane active">
							<h4 class="glyphicons group"><i></i><spring:message code="info.aboutUs.heading"/></h4>
							<div class="slim-scroll" data-scroll-height="215px">
								<spring:message code="info.aboutUs.desc"/>
							</div>
						</div>
						<div id="services" class="tab-pane">
							<h4 class="glyphicons coins"><i></i><spring:message code="info.services.heading"/></h4>
							<div class="slim-scroll" data-scroll-height="215px">
								<spring:message code="info.services.desc"/>
							</div>
						</div>
						<div id="legal" class="tab-pane">
							<h4 class="glyphicons tie"><i></i><spring:message code="info.legal.heading"/></h4>
							<div class="slim-scroll" data-scroll-height="215px">
								<spring:message code="info.legal.desc"/>
							</div>
						</div>
						<div id="privacy" class="tab-pane">
							<h4 class="glyphicons keys"><i></i><spring:message code="info.privacy.heading"/></h4>
							<div class="slim-scroll" data-scroll-height="215px">
								<spring:message code="contact.email.legal" var="emailLegal"/>
								<spring:message code="info.privacy.desc" arguments="${emailLegal}"/>
							</div>
						</div>
						<div id="contact" class="tab-pane">
							<h4 class="glyphicons phone"><i></i><spring:message code="info.contact.heading"/></h4>
							<div class="slim-scroll" data-scroll-height="215px">
								<spring:message code="contact.email.contact" var="emailContact"/>
								<spring:message code="contact.email.support" var="emailSupport"/>
								<spring:message code="contact.phone.phone1" var="phone1"/>
								<spring:message code="contact.phone.phone2" var="phone2"/>
								<spring:message code="info.contact.desc" 
									arguments="${emailContact},${emailSupport},${phone1},${phone2}"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Landing Page Footer Extension - start -->
<div class="separator-line margin-none"></div>
<div class="container-960">
	<div class="row-fluid row-merge">
		<div class="span6 innerT innerLR">
			<div class="span1 glyphicons circle_info" title="<spring:message code="info.news.heading"/>" data-toggle="tooltip">
				<i></i>
			</div>
			<div id="news">
				<%@include file="news.jsp" %>
			</div>
		</div>
		<div class="span6">
			<div class="social-large tab_links">
				<a href="#about_us" class="glyphicons group active" data-toggle="tab"><i></i><spring:message code="info.aboutUs.heading"/></a>
				<a href="#services" class="glyphicons coins" data-toggle="tab"><i></i><spring:message code="info.services.heading"/></a>
				<a href="#legal" class="glyphicons tie" data-toggle="tab"><i></i><spring:message code="info.legal.heading"/></a>
				<a href="#privacy" class="glyphicons keys" data-toggle="tab"><i></i><spring:message code="info.privacy.heading"/></a>
				<a href="#contact" class="glyphicons phone" data-toggle="tab"><i></i><spring:message code="info.contact.heading"/></a>
			</div>
		</div>
	</div>
</div>
<!-- Landing Page Footer Extension - end -->	
	
<!-- Forgot Password Modal Holder -->
<div class="modal fade" id="forgotPasswordModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
			</div>
		</div>
	</div>
</div>
<!-- // Forgot Password Modal Holder END -->
<!-- // login.jsp END-->