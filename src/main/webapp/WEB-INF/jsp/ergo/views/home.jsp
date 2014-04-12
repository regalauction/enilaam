<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<div class="separator"></div>
<div class="wrapper">
	<div class="container-960">
		<div class="row-fluid">
			<div class="span6">
				<h3><spring:message code="welcome" arguments="${userDisplayName}"/></h3>
				<div class="separator"></div>
				
				<security:authorize ifAnyGranted="ROLE_BIDDER">
				<c:if test="${numRunningAuctions gt 0}">
					<p>You can place bid on <a href="<spring:url value="/auction/bid" />"><c:out value="${numRunningAuctions}"/> running auctions.</a></p>
				</c:if>
				</security:authorize>
				
				<security:authorize ifAnyGranted="ROLE_OBSERVER">
				<c:if test="${numRunningAuctions gt 0}">
					<p>You can observe <a href="<spring:url value="/auction/bid" />"><c:out value="${numRunningAuctions}"/> running auctions.</a></p>
				</c:if>
				</security:authorize>
				
				<c:if test="${hasUpcommingAuctions}">
					<p>Check <a href="<spring:url value="/auction/upcoming" />">upcomming auctions</a> on <spring:message code="appname" />.</p>
				</c:if>
				
				<security:authorize ifAnyGranted="ROLE_BIDDER">
				<p>
				<c:choose>
					<c:when test="${hasAuctionHistory}">
						Find the auctions you have participated previously in your <a href="<spring:url value="/auction/history" />">auction history.</a>
					</c:when>
					<c:otherwise>You have not participated in any auctions yet.</c:otherwise>
				</c:choose>
				</p>
				</security:authorize>
			</div>
			<div class="span6">
				<div class="widget">
					<div class="widget-head">
						<h4 class="heading glyphicons circle_info"><i></i><spring:message code="info.news.heading"/></h4>
					</div>
					<div class="widget-body">
						<div id="news">
							<ul>
								<li>1. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard</li>
								<li>2. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard</li>
								<li>3. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard</li>
							</ul>
						</div>
					</div>
					<div class="widget-footer">
						<a id="news_next" class="glyphicons step_forward" href="javascript: void(0);"><i></i></a>
						<a id="news_prev" class="glyphicons step_backward" href="javascript: void(0);"><i></i></a>
					</div>
				</div>
				
				<div class="widget">
					<div class="widget-head">
						<h4 class="heading glyphicons phone"><i></i><spring:message code="info.contact.heading"/></h4>
					</div>
					<div class="widget-body">
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