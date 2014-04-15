<!-- dashboard.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set var="delayMins" value="5"/>
<c:set var="delayMilis" value="${delayMins * 60 * 1000}"/>
<div id="dashboard-running" class="widget" data-delay="${delayMilis}">
	<div class="widget-head">
		<h4 class="heading glyphicons cardio"><i></i><spring:message code="auction.running"/></h4>
		<span class="pull-right">
			<a id="dashboard-running-refresh" href="<spring:url value="/auction/running"/>" class="btn btn-primary btn-small btn-block btn-icon glyphicons refresh"><i></i><spring:message code="common.refresh"/></a>
		</span>
		<span class="pull-right">
			<span id="dashboard-running-refresh-tip" class="muted"><spring:message code="dashboard.running.refresh.tip" arguments="${delayMins}"/>&nbsp;&nbsp;</span>
			<span id="dashboard-running-refresh-loader" class="muted"><spring:message code="auction.refresh.loader"/>&nbsp;&nbsp;</span>
		</span>
	</div>
	<div id="dashboard-running-list" class="widget-body">
	</div>
</div>
<!-- // dashboard.jsp END -->