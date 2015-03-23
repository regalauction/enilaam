<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- Authenticated page top navigation bar - start -->
<div class="navbar main">

	<div class="wrapper">
	
		<!-- Menu Toggle Button -->
		<button type="button" class="btn btn-navbar">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
		</button>
		<!-- // Menu Toggle Button END -->
		
		<%-- Uncomment after Notification system is ready --%>
		<%-- 
		<!-- Notification Menu -->		
		<ul class="topnav pull-left tn1 hidden-phone">
			<li class="dropdown dd-1 dd-2">
				<a href="" data-toggle="dropdown">Notifications <span class="count">3</span></a>
				<ul class="dropdown-menu pull-left">
					<li><a href="#" class="glyphicons envelope"><i></i> New Email</a></li>
                    <li><a href="#" class="glyphicons chat"><i></i> 5 Messages</a></li>
                    <li><a href="#" class="glyphicons conversation"><i></i> 1 New Reply</a></li>
				</ul>
			</li>
			
		</ul>
		<!-- // Notification Menu END -->
		 --%>
		
		<!-- Profile Menu -->				
		<ul class="topnav pull-right">
			<tiles:insertAttribute name="profileMenu" ignore="false" />	
		</ul>
		<!-- // Profile Menu END -->
		
		<!-- System clock -->
		<div id="sysclock" class="topnav pull-right" title="<spring:message code="common.sysclock.title"/>">
			<c:out value="${systime}"/>
		</div>
						
		<div class="clearfix"></div>
	</div>
</div>
<!-- Authenticated page top navigation bar - end -->