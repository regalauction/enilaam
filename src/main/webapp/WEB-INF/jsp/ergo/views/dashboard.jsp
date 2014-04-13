<!-- dashboard.jsp -->
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="running" class="widget" data-url="<spring:url value="/auction/running"/>">
	<div class="widget-head">
		<h4 class="heading glyphicons cardio"><i></i><spring:message code="auction.running"/></h4>
		<span class="pull-right">
			<a id="dashboard-running-refresh" href="<spring:url value="/auction/running"/>" class="btn btn-primary btn-small btn-block btn-icon glyphicons refresh"><i></i><spring:message code="common.refresh"/></a>
		</span>
		<span class="pull-right">
			<span id="dashboard-running-refresh-tip" class="muted"><spring:message code="dashboard.running.refresh.tip"/>&nbsp;&nbsp;</span>
			<span id="dashboard-running-refresh-loader" class="muted"><spring:message code="auction.refresh.loader"/>&nbsp;&nbsp;</span>
		</span>
	</div>
	<div id="dashboard-running-list" class="widget-body">
	</div>
</div>
<!-- // dashboard.jsp END -->