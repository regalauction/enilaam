<!-- template.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- <spring:message code="appname"/> <spring:theme code="project.version"/> -->

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="ie lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>    <html class="ie lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>    <html class="ie lt-ie9"> <![endif]-->
<!--[if gt IE 8]> <html class="ie gt-ie8"> <![endif]-->
<!--[if !IE]><!--><html><!-- <![endif]-->

<%
	String userAgent = (String) request.getHeader("user-agent");
	if (userAgent!=null && userAgent.indexOf("MSIE") != -1) {
%>
<head>
	<title><spring:message code="browser.unsupported.heading"/></title>
	<link href="themes/ergo/bootstrap/css/bootstrap.css" rel="stylesheet" />
	<link href="themes/ergo/theme/css/style-light.css" rel="stylesheet" />
</head>
<body style="background-color: #F2F3F4;">
	<p class="center"><spring:message code="browser.unsupported.text"/></p>
</body>
<% } else { %>


<head profile="http://www.w3.org/2005/10/profile">
	<link rel="icon" type="image/png" href="<spring:theme code="image.auction.winning"/>">
	
	<%-- Set the base url for the application. This is needed for portability on different browsers --%>
	<spring:theme code="html.base.href" var="baseUrl"/>
	<base href="${baseUrl}">
	
	<%-- Page Title - start --%>
	<title> : <spring:message code="appname"/> : <tiles:getAsString name="title" ignore="true" /></title>
	<%-- Page Title - end --%>

	<!-- Meta -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE" />

<spring:theme code="css.doNotUseCompressed" var="cssDoNotUseCompressed"/>
<c:choose>
	<c:when test="${cssDoNotUseCompressed}">
		<!-- Bootstrap -->
		<link href="themes/ergo/bootstrap/css/bootstrap.css" rel="stylesheet" />
		<link href="themes/ergo/bootstrap/css/responsive.css" rel="stylesheet" />
	
		<!-- Glyphicons Font Icons -->
		<link href="themes/ergo/theme/css/glyphicons.css" rel="stylesheet" />
		
		<!-- Uniform Pretty Checkboxes -->
		<link href="themes/ergo/theme/scripts/plugins/forms/pixelmatrix-uniform/css/uniform.default.css" rel="stylesheet" />
			
		<!-- Bootstrap Extended -->
		<link href="themes/ergo/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap.css" rel="stylesheet">
		<link href="themes/ergo/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap-responsive.css" rel="stylesheet">
		<link href="themes/ergo/bootstrap/extend/bootstrap-wysihtml5/css/bootstrap-wysihtml5-0.0.2.css" rel="stylesheet">
		<link href="themes/ergo/bootstrap/extend/bootstrap-select/bootstrap-select.css" rel="stylesheet" />
		<link href="themes/ergo/bootstrap/extend/bootstrap-toggle-buttons/static/stylesheets/bootstrap-toggle-buttons.css" rel="stylesheet" />
		
		<!-- Select2 Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/forms/select2/select2.css" rel="stylesheet" />
		
		<!-- DateTimePicker Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/forms/bootstrap-datetimepicker/css/datetimepicker.css" rel="stylesheet" />
		
		<!-- JQueryUI -->
		<link href="themes/ergo/theme/scripts/plugins/system/jquery-ui/css/smoothness/jquery-ui-1.9.2.custom.css" rel="stylesheet" />
		
		<!-- MiniColors ColorPicker Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.css" rel="stylesheet" />
		
		<!-- Notyfy Notifications Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/notifications/notyfy/jquery.notyfy.css" rel="stylesheet" />
		<link href="themes/ergo/theme/scripts/plugins/notifications/notyfy/themes/default.css" rel="stylesheet" />
		
		<!-- Gritter Notifications Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/notifications/Gritter/css/jquery.gritter.css" rel="stylesheet" />
		
		<!-- DataTables Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/tables/DataTables/media/css/DT_bootstrap.css" rel="stylesheet" />
	
		<!-- Bootstrap Image Gallery -->
		<link href="themes/ergo/bootstrap/extend/bootstrap-image-gallery/css/bootstrap-image-gallery.css" rel="stylesheet" />
		
		<!-- Plupload File Manager Plugin -->
		<style type="text/css">@import url(themes/ergo/theme/scripts/plugins/forms/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css);</style>
		
		<!-- Main Theme Stylesheet :: CSS -->
		<link href="themes/ergo/theme/css/style-light.css" rel="stylesheet" />
		
		<!-- Custom Stylesheet -->
		<link href="themes/ergo/custom/css/style.css" rel="stylesheet" />	
	</c:when>
	<c:otherwise>
		<!-- Bootstrap -->
		<link href="themes/ergo/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link href="themes/ergo/bootstrap/css/responsive.min.css" rel="stylesheet" />
	
		<!-- Glyphicons Font Icons -->
		<link href="themes/ergo/theme/css/glyphicons.min.css" rel="stylesheet" />
		
		<!-- Uniform Pretty Checkboxes -->
		<link href="themes/ergo/theme/scripts/plugins/forms/pixelmatrix-uniform/css/uniform.default.min.css" rel="stylesheet" />
			
		<!-- Bootstrap Extended -->
		<link href="themes/ergo/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap.min.css" rel="stylesheet">
		<link href="themes/ergo/bootstrap/extend/jasny-bootstrap/css/jasny-bootstrap-responsive.min.css" rel="stylesheet">
		<link href="themes/ergo/bootstrap/extend/bootstrap-wysihtml5/css/bootstrap-wysihtml5-0.0.2.min.css" rel="stylesheet">
		<link href="themes/ergo/bootstrap/extend/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" />
		<link href="themes/ergo/bootstrap/extend/bootstrap-toggle-buttons/static/stylesheets/bootstrap-toggle-buttons.min.css" rel="stylesheet" />
		
		<!-- Select2 Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/forms/select2/select2.min.css" rel="stylesheet" />
		
		<!-- DateTimePicker Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/forms/bootstrap-datetimepicker/css/datetimepicker.min.css" rel="stylesheet" />
		
		<!-- JQueryUI -->
		<link href="themes/ergo/theme/scripts/plugins/system/jquery-ui/css/smoothness/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" />
		
		<!-- MiniColors ColorPicker Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.min.css" rel="stylesheet" />
		
		<!-- Notyfy Notifications Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/notifications/notyfy/jquery.notyfy.min.css" rel="stylesheet" />
		<link href="themes/ergo/theme/scripts/plugins/notifications/notyfy/themes/default.min.css" rel="stylesheet" />
		
		<!-- Gritter Notifications Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/notifications/Gritter/css/jquery.gritter.min.css" rel="stylesheet" />
		
		<!-- DataTables Plugin -->
		<link href="themes/ergo/theme/scripts/plugins/tables/DataTables/media/css/DT_bootstrap.min.css" rel="stylesheet" />
	
		<!-- Bootstrap Image Gallery -->
		<link href="themes/ergo/bootstrap/extend/bootstrap-image-gallery/css/bootstrap-image-gallery.min.css" rel="stylesheet" />
		
		<!-- Plupload File Manager Plugin -->
		<style type="text/css">@import url(themes/ergo/theme/scripts/plugins/forms/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.min.css);</style>
		
		<!-- Main Theme Stylesheet :: CSS -->
		<link href="themes/ergo/theme/css/style-light.min.css" rel="stylesheet" />
		
		<!-- Custom Stylesheet -->
		<link href="themes/ergo/custom/css/style.min.css" rel="stylesheet" />		
	</c:otherwise>
</c:choose>	

	
	<!--[if IE]><!--><script src="themes/ergo/theme/scripts/plugins/other/excanvas/excanvas.js"></script><!--<![endif]-->
	<!--[if lt IE 8]><script src="themes/ergo/theme/scripts/plugins/other/json2.js"></script><![endif]-->
	
	<style type="text/css">
	#preloader {
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		height: 100%;
		z-index: 9998;
		background: url(<spring:theme code="image.preloader"/>) 50% 50% no-repeat #F2F3F4;
	}
	#jsrequired {
		position: fixed;
		left: 0px;
		top: 0px;
		width: 100%;
		height: 100%;
		z-index: 9999;
		background-color: #F2F3F4;
	}
	</style>
	
	<script src="themes/ergo/theme/scripts/plugins/system/jquery.min.js"></script>
	<!-- display preloader -->
	<script type="text/javascript">
		$(window).load(function() {
			$("#preloader").fadeOut("slow"); 
		});
	</script>
</head>

<security:authentication property="principal.username" var="username" scope="request"/>
<security:authentication property="principal.domainUser" var="user" scope="request"/>
<c:set var="userDisplayName" value="" scope="request"/>
<c:choose>
	<c:when test="${not empty user.firstName and not empty user.lastName}">
		<c:set var="userDisplayName" value="${user.firstName}&nbsp;${user.lastName}" scope="request"/>
	</c:when>
	<c:when test="${not empty user.firstName and empty user.lastName}">
		<c:set var="userDisplayName" value="${user.firstName}" scope="request"/>
	</c:when>
	<c:when test="${empty user.firstName and not empty user.lastName}">
		<c:set var="userDisplayName" value="${user.lastName}" scope="request"/>
	</c:when>
	<c:when test="${empty user.firstName and empty user.lastName}">
		<c:set var="userDisplayName" value="${user.username}" scope="request"/>
	</c:when>
</c:choose>

<body>
	<div id="jsrequired">
		<p class="text-error center"><strong>Sorry!</strong><br />The browser needs to allow javascript.</p>
	</div>
	<script type="text/javascript">
		document.getElementById("jsrequired").style.display = "none";
	</script>
	
	<!-- preloader -->
	<div id="preloader"></div>
	
	<!-- User pages have "fluid" layout. Admin pages have "fixed" layout. -->
	<div class="container-fluid <tiles:getAsString name="layout" ignore="false"/>">
	
		<tiles:insertAttribute name="topbarNav"/>
		
		<tiles:insertAttribute name="wrappedContent"/>
		
		<!-- uncomment the following to display footer -->
		<!-- <div class="fixedbar">
			<div id="footer" class="floatingbox">
				<div class="copy">&copy; 2014 Regal Auction - All Rights Reserved. Developed and maintained by Regal Auction.</div>
			</div>
		</div> -->
	</div>
	
	<!-- Global -->
	<script>
	var basePath = '';
	</script>

<spring:theme code="js.doNotUseCompressed" var="jsDoNotUseCompressed"/>
<c:choose>
	<c:when test="${jsDoNotUseCompressed}">
		
		<!-- JQueryUI -->
		<script src="themes/ergo/theme/scripts/plugins/system/jquery-ui/js/jquery-ui-1.9.2.custom.js"></script>
		
		<!-- JQueryUI Touch Punch -->
		<!-- small hack that enables the use of touch events on sites using the jQuery UI user interface library -->
		<script src="themes/ergo/theme/scripts/plugins/system/jquery-ui-touch-punch/jquery.ui.touch-punch.js"></script>
		
		
		<!-- Modernizr -->
		<script src="themes/ergo/theme/scripts/plugins/system/modernizr.js"></script>
		
		<!-- Bootstrap -->
		<script src="themes/ergo/bootstrap/js/bootstrap.js"></script>
		
		<!-- SlimScroll Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/other/jquery-slimScroll/jquery.slimscroll.js"></script>
		
		<!-- Common Demo Script -->
		<script src="themes/ergo/theme/scripts/demo/common.js"></script>
		
		<!-- Holder Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/other/holder/holder.js"></script>
		
		<!-- Uniform Forms Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/forms/pixelmatrix-uniform/jquery.uniform.js"></script>

		<!-- Bootstrap Extended -->
		<script src="themes/ergo/bootstrap/extend/bootstrap-select/bootstrap-select.js"></script>
		<script src="themes/ergo/bootstrap/extend/bootstrap-toggle-buttons/static/js/jquery.toggle.buttons.js"></script>
		<script src="themes/ergo/bootstrap/extend/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.js"></script>
		<script src="themes/ergo/bootstrap/extend/jasny-bootstrap/js/jasny-bootstrap.js"></script>
		<script src="themes/ergo/bootstrap/extend/jasny-bootstrap/js/bootstrap-fileupload.js"></script>
		<script src="themes/ergo/bootstrap/extend/bootbox.js"></script>
		<script src="themes/ergo/bootstrap/extend/bootstrap-wysihtml5/js/wysihtml5-0.3.0_rc2.js"></script>
		<script src="themes/ergo/bootstrap/extend/bootstrap-wysihtml5/js/bootstrap-wysihtml5-0.0.2.js"></script>
		
		<!-- Google Code Prettify -->
		<script src="themes/ergo/theme/scripts/plugins/other/google-code-prettify/prettify.js"></script>
		
		<!-- Gritter Notifications Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/notifications/Gritter/js/jquery.gritter.js"></script>
		
		<!-- Notyfy Notifications Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/notifications/notyfy/jquery.notyfy.js"></script>
		
		<!-- MiniColors Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/color/jquery-miniColors/jquery.miniColors.js"></script>
		
		<!-- DateTimePicker Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/forms/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	
		<!-- Cookie Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/system/jquery.cookie.js"></script>
		
		<!-- Easy-pie Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/charts/easy-pie/jquery.easy-pie-chart.js"></script>
		
		<!-- Sparkline Charts Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/charts/sparkline/jquery.sparkline.min.js"></script>
		
		<!-- Ba-Resize Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/other/jquery.ba-resize.js"></script>
		
		<!-- DataTables Tables Plugin -->
		<script src="themes/ergo/theme/scripts/plugins/tables/DataTables/media/js/jquery.dataTables.js"></script>
		<script src="themes/ergo/theme/scripts/plugins/tables/DataTables/media/js/DT_bootstrap.js"></script>
		
		<!-- Easy-ticker Plugin -->
		<script src="themes/ergo/jquery.easy-ticker/jquery.easy-ticker.js"></script>
		
		<script src="themes/ergo/theme/scripts/plugins/forms/plupload-2.1.1/js/plupload.full.min.js"></script>
		
		<!-- Load plupload and all it's runtimes and finally the jQuery queue widget -->
		<%--
		<script src="themes/ergo/theme/scripts/plugins/forms/plupload/js/plupload.full.js"></script>
		<script src="themes/ergo/theme/scripts/plugins/forms/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
		 --%>
		 
		<!-- Custom JavaScript -->
		<script src="themes/ergo/custom/scripts/script.js"></script>
	</c:when>
	<c:otherwise>
		<script src="<spring:theme code="js.compressed"/>"></script>
	</c:otherwise>
</c:choose>	
</body>
</html>

<% } %>

<!-- // template.jsp END -->