<!-- sidebarNav.jsp -->
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!-- Sidebar Menu -->
<div id="menu" class="hidden-phone hidden-print">

	<!-- Brand -->
	<a href="#" class="appbrand">Regal Auction <span>v2.0</span></a>

	<!-- Scrollable menu wrapper with Maximum height -->
	<div class="slim-scroll" data-scroll-height="400px">
	
	<!-- Regular Size Menu -->
	<ul class="menu-0">
	
						
		<!-- Menu Regular Item -->
		<li class="glyphicons display active"><a href="<spring:url value="/"/>"><i></i><span>Dashboard</span></a></li>
		
		<security:authorize ifAnyGranted="ROLE_ADMIN">
		<!-- Components Submenu Level 1 -->
		<li class="hasSubmenu glyphicons cogwheels">
			<a data-toggle="collapse" href="#menu_components"><i></i><span>Components</span></a>
			<ul class="collapse" id="menu_components">
				
				<!-- Components Submenu Level 2 -->
				<li class="hasSubmenu">
					<a data-toggle="collapse" href="#menu_auctions"><span><spring:message code="menu.auction"/></span></a>
					<ul class="collapse" id="menu_auctions">
						<li><a href="<spring:url value="/auction/new" />"><span><spring:message code="menu.auction.new"/></span></a></li>
						<li><a href="<spring:url value="/auction" />"><span><spring:message code="auction.list"/></span></a></li>
					</ul>
					<span class="count">2</span>
				</li>
				
				<li class="hasSubmenu">
					<a data-toggle="collapse" href="#menu_items"><span><spring:message code="menu.item"/></span></a>
					<ul class="collapse" id="menu_items">
						<li><a href="<spring:url value="/item/new" />"><span><spring:message code="menu.item.new"/></span></a></li>
						<li><a href="<spring:url value="/item" />"><span><spring:message code="item.list"/></span></a></li>
					</ul>
					<span class="count">2</span>
				</li>
				
				<li class="hasSubmenu">
					<a data-toggle="collapse" href="#menu_users"><span><spring:message code="menu.user"/></span></a>
					<ul class="collapse" id="menu_users">
						<li><a href="<spring:url value="/user/new" />"><span><spring:message code="menu.user.new"/></span></a></li>
						<li><a href="<spring:url value="/user" />"><span><spring:message code="user.list"/></span></a></li>
					</ul>
					<span class="count">2</span>
				</li>
				
				<li class="hasSubmenu">
					<a data-toggle="collapse" href="#menu_news"><span>News</span></a>
					<ul class="collapse" id="menu_news">
						<li class=""><a href=""><span>New</span></a></li>
						<li class=""><a href=""><span>List</span></a></li>
					</ul>
					<span class="count">2</span>
				</li>
				<!-- // Components Submenu Level 2 END -->
				
			</ul>
			<span class="count">4</span>
		</li>
		<!-- Components Submenu Level 1 END -->
		
		<!-- Wizards Submenu Level 1 -->
		<li class="hasSubmenu">
		
			<a data-toggle="collapse" class="glyphicons magic" href="#menu_wizards"><i></i><span>Wizards</span></a>
			<ul class="collapse" id="menu_wizards">
			
				<!-- Wizards Submenu Regular Items -->
				<li class=""><a href=""><i></i><span>Quick Create Auction</span></a></li>
				<!-- // Wizards Submenu Regular Items END -->
				
			</ul>
			<span class="count">1</span>
		</li>
		<!-- // Wizards Submenu Level 1 END -->
		
		<!-- Reports Submenu Level 1 -->
		<li class="hasSubmenu">
		
			<a data-toggle="collapse" class="glyphicons charts" href="#menu_reports"><i></i><span>Reports</span></a>
			<ul class="collapse" id="menu_reports">
			
				<!-- Wizards Submenu Regular Items -->
				<li class=""><a href=""><i></i><span>Bid Details Report</span></a></li>
				<li class=""><a href=""><i></i><span>Winner Report</span></a></li>
				<!-- // Wizards Submenu Regular Items END -->
				
			</ul>
			<span class="count">2</span>
		</li>
		<!-- // Reports Submenu Level 1 END -->
		
		</security:authorize>		
	</ul>
	<div class="clearfix"></div>
	<!-- // Regular Size Menu END -->
	
	<security:authorize ifAnyGranted="ROLE_ADMIN">
	<ul class="menu-1">
		<li class="hasSubmenu active">
			<a class="glyphicons wifi_alt" href="#menu-recent-stats" data-toggle="collapse"><i></i><span>Recent stats</span></a>
			<ul class="collapse in" id="menu-recent-stats">
				<li><a class="glyphicons stopwatch" href="#"><i></i><span>5 Running Auctions</span></a></li>
				<li><a class="glyphicons binoculars" href="#"><i></i><span>10 Upcomming Auctions</span></a></li>
				<li><a class="glyphicons user" href="#"><i></i><span>15 Bidders Online</span></a></li>
				<li><a class="glyphicons single circle_plus" href="#"><i></i><span>View all</span></a></li>
			</ul>
		</li>
	</ul>
	<div class="clearfix"></div>
	</security:authorize>
	
	<div class="separator bottom"></div>
	
	
	</div>
	<!-- // Scrollable Menu wrapper with Maximum Height END -->
	
</div>
<!-- // Sidebar Menu END -->
<!-- // sidebarNav.jsp END -->