<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!-- Authenticated page top navigation bar - start -->
<div class="navbar main">

	<div class="wrapper">
	
		<div class="container-960">
		
			<!-- Top Menu Right -->
			<ul class="topnav pull-left">
				<li>
					<a href="<spring:url value="/home" />" 
						title="<spring:message code="menu.home.title"/>" 
						class="glyphicons home">
						<i></i><spring:message code="menu.home"/>
					</a>
				</li>
				<li>
					<a href="<spring:url value="/auction/bid" />" 
						title="<spring:message 
						code="menu.auction.running.title"/>" 
						class="glyphicons stopwatch">
						<i></i><spring:message code="menu.auction.running"/>
					</a>
				</li>
				<li>
					<a href="<spring:url value="/auction/upcoming" />" 
						title="<spring:message code="menu.auction.upcoming.title"/>"
						class="glyphicons binoculars">
						<i></i><spring:message code="menu.auction.upcoming"/>
					</a>
				</li>
				<li>
					<a href="<spring:url value="/auction/history" />" 
						title="<spring:message code="menu.account.auctionhistory.title"/>"
						class="glyphicons history">
						<i></i><spring:message code="menu.account.auctionhistory"/></a>
				</li>
			</ul>
			
			<!-- Profile Menu -->
			<ul class="topnav pull-right margin-none">
				<tiles:insertAttribute name="profileMenu" ignore="false" />	
			</ul>
			<!-- // Profile Menu END -->
			
		</div>
		
		<div class="clearfix"></div>
		
	</div>
	
</div>
<!-- Authenticated page top navigation bar - end -->