<!--  bid.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="/WEB-INF/tlds/util.tld" prefix="util"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="widget">
	<div class="widget-head">
		<h4 class="heading glyphicons stopwatch"><i></i><spring:message code="auction.running"/></h4>
		<span class="pull-right">
			<a id="auction-refresh" href="<spring:url value="auction/bid"/>" class="btn btn-primary btn-small btn-block btn-icon glyphicons refresh"><i></i><spring:message code="common.refresh"/></a>
		</span>
		<span class="pull-right">
			<span id="auction-refresh-tip" class="muted"><spring:message code="auction.refresh.tip"/>&nbsp;&nbsp;</span>
			<span id="auction-refresh-loader" class="muted"><spring:message code="auction.refresh.loader"/>&nbsp;&nbsp;</span>
		</span>
	</div>
	<div id="auction" class="widget-body">
		<c:choose>
			<c:when test="${empty auctions}">
				<spring:message code="auction.running.empty"/>
			</c:when>
			<c:otherwise>
				<div class="heading row-fluid">
					<div class="span3 info"><strong><spring:message code="bidding.auction.name"/></strong></div>
					<div class="span1 center"><strong><spring:message code="bidding.auction.basePrice"/></strong> <sup title="base price with which an auction starts" data-toggle="tooltip">?</sup></div>
					<div class="span1 center"><strong><spring:message code="bidding.auction.leadPrice"/></strong> <sup title="leading price amount for an auction" data-toggle="tooltip">?</sup></div>
					<div class="span1 center"><strong><spring:message code="bidding.auction.changePrice"/></strong> <sup title="minimum price amount by which your bid should differ from the leading bid" data-toggle="tooltip">?</sup></div>
					<div class="span1 center"><strong><spring:message code="bidding.auction.timeleft"/></strong></div>
					<div class="span5 center"><strong><spring:message code="bidding.auction.bid"/></strong></div>
				</div>
				<hr />
				<div id="auction-list">
					<c:forEach items="${auctions}" var="auction" varStatus="status">
						<%@include file="auctionRow.jsp" %>
						<c:if test="${not status.last}"><hr /></c:if>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</div>	
<!-- Gallery Modal Holder -->
<div class="modal hide fade" id="galleryModal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4><spring:message code="common.gallery"/></h4>
				<button type="button" class="close" data-dismiss="modal">×</button>
			</div>
			<div class="modal-body">
			</div>
		</div>
	</div>
</div>
<!-- // Gallery Modal Holder END -->
<!-- // bid.jsp END -->
