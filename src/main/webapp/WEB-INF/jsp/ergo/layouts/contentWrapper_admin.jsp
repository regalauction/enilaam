<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!-- Sidebar menu & content wrapper -->
<div id="wrapper">
	
	<tiles:insertAttribute name="sidebarNav" ignore="false" />
	
	<div id="content">
		<tiles:insertAttribute name="content" ignore="false" />
	</div>

</div>

<div class="clearfix"></div>
<!-- // Sidebar menu & content wrapper END -->
		
<div class="separator-line margin-none"></div>
