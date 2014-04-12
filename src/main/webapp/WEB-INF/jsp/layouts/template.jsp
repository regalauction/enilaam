<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
<spring:message code="html.base.href" var="baseUrl"/>
<base href="${baseUrl}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<spring:theme code="css.blueprint.screen" />">
<!-- [if IE]>
<link rel="stylesheet" href="<spring:theme code="css.blueprint.ie" />">
<style type="text/css">legend { margin-bottom: 1.4em; }</style>
<![endif]-->
<link rel="stylesheet" href="<spring:theme code="css.blueprint.plugins.buttons.screen" />">
<link rel="stylesheet" href="<spring:theme code="css.main" />">
<link rel="stylesheet" href="<spring:theme code="css.stickyfooter" />">
<link rel="stylesheet" href="<spring:theme code="css.menu.dropdown" />">
<link rel="stylesheet" href="<spring:theme code="css.webrupee" />">
<link rel="stylesheet" href="<spring:theme code="css.jquery.datatable" />">

<script type="text/javascript" src="<spring:theme code="js.jquery" />"></script>
<script type="text/javascript" src="<spring:theme code="js.jquery.datatable" />"></script>

<title> : <spring:message code="appname"/> : <tiles:getAsString name="title" ignore="true" /></title>
</head>
<body>
<div id="wrap">
	<div id="main" class="main container">
		<div class="span-24 append-bottom">
			<tiles:insertAttribute name="header" />
			<security:authorize ifAnyGranted="ROLE_USER">
				<security:authentication property="principal" var="user"/>
				<div class="span-18">
					<tiles:insertAttribute name="menu" ignore="true" />
				</div>
				<div class="span-6 last" style="text-align: right;">
					<a href="<spring:url value="/profile" />"><c:out value="${user.username}"/></a>
					<spring:message code="header.link.seperator"/>
					<a href="<spring:url value="/j_spring_security_logout" />"><spring:message code="logout" /></a>
				</div>
			</security:authorize>
		</div>
		<div class="span-24"><tiles:insertAttribute name="body" ignore="true" /></div>
	</div>
</div>
<div id="footer" class="container">
	<tiles:insertAttribute name="footer" />
</div>
</body>
</html>