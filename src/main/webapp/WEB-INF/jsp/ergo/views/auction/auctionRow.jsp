<!-- auctionRow.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/tlds/util.tld" prefix="util"%>

<div id="auction_${auction.auctionCode}" class="row-fluid">
	<div class="span3 info">
		<div class="row-fluid"><c:out value="${auction.name}" /></div>
		<div class="row-fluid">
			<div class="span2 center">
				<c:if test="${auction.winner}">
					<img alt="hammer" title="<spring:message code="auction.winning"/>"  data-toggle="tooltip" src="<spring:theme code="image.auction.winning"/>">
				</c:if>
			</div>
			<c:if test="${not empty auction.item.images}">
				<div class="span2 center" title="click to view photos of this item" data-toggle="tooltip">
					<spring:url value="/auction/{auctionCode}/images" var="imagesUrl">
						<spring:param name="auctionCode" value="${auction.auctionCode}"/>
					</spring:url>
					<a href="${imagesUrl}" data-toggle="modal" data-target="#galleryModal" class="glyphicons camera"><i></i></a>
				</div>
			</c:if>
			<c:if test="${not empty auction.documents}">
				<div class="span2 center">
					<a href="javascript: void(0);" title="click to view attachments of this item" data-toggle="tooltip" class="attachments glyphicons paperclip"><i></i></a>
				</div>
			</c:if>
			<div class="span6"></div>
		</div>
		<div class="row-fluid margin2">
			<c:if test="${not empty auction.documents}">
				<ul class="attachments small hide">
					<c:forEach items="${auction.documents}" var="document">
						<li><a href="<spring:url value="/attachments/documents/${document.code}"/>"><small><c:out value="${document.name}"/></small></a></li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
	</div>
	
	<div class="span1 center">
		<spring:message code="common.currencySymbol"/>&nbsp;<fmt:formatNumber value="${auction.basePrice.amount}" groupingUsed="true" minFractionDigits="0" maxFractionDigits="2"/>
	</div>
	
	<div class="span1 center">
		<!-- Add class "text-success" when the bidder is winning -->
		<c:if test="${not auction.noBid}">
			<span <c:if test="${auction.winner}">class="text-success"</c:if>>
				<spring:message code="common.currencySymbol"/>&nbsp;
				<fmt:formatNumber value="${auction.leadPrice.amount}" groupingUsed="true" minFractionDigits="0" maxFractionDigits="2"/>
			</span>
		</c:if>
	</div>

	<div class="span1 center">
		<spring:message code="common.currencySymbol"/>&nbsp;
		<fmt:formatNumber value="${auction.deltaPrice.amount}" groupingUsed="true" minFractionDigits="0" maxFractionDigits="2"/>
	</div>
	
	<div class="span1 center">
		<span <c:if test="${auction.goingToEnd}">class="text-warning"</c:if>>
			<util:period date="${auction.endDate}" />
		</span>
	</div>
	
	<c:set var="hasValidationError" value="false"/>
	<fmt:formatNumber var="nextBidPriceAmount"
		value="${auction.nextBidPrice.amount}" 
		groupingUsed="true" 
		minFractionDigits="2" 
		maxFractionDigits="2"/>
	<spring:hasBindErrors name="auctionRow">
		<spring:bind path="auctionRow.bidPrice">
			<c:set var="hasValidationError" value="true"/>
			<c:set var="nextBidPriceAmount" value="${status.value}"/>
			<c:set var="errorMessage" value="${status.errorMessage}"/>
		</spring:bind>
	</spring:hasBindErrors>
	<div class="span5 center row-fluid">
		<!-- Add class "error" to this element for bid result error -->
		<div class="control-group span7 <c:if test="${bidResult.error or hasValidationError}">error</c:if>">
			<div class="controls">
				<div class="input-prepend input-append bidprice">
					<span class="add-on minus">
						<strong>&minus;</strong>
					</span>
					<span class="add-on disabled">
						<spring:message code="common.currencySymbol"/>
					</span>
					<input type="text" 
						id="bidPrice_${auction.auctionCode}" 
						name="bidPrice_${auction.auctionCode}" 
						data-change="${auction.deltaPrice}" 
						value="${nextBidPriceAmount}" 
						class="span6 right"/>
					<span class="add-on plus">
						<strong>+</strong>
					</span>
				</div>
				<c:choose>
					<c:when test="${bidResult.error}">
						<span class="label label-important">
					 		<spring:message code="${bidResult.code}"/>
					 	</span>
					</c:when>
					<c:when test="${hasValidationError}">
				 		<span class="label label-important">
							<c:out value="${errorMessage}"/>
						</span>
					</c:when>
				</c:choose>
			</div>
		</div>
		
		<div class="span2">
			<label class="checkbox uniformjs" data-original-title="<spring:message code="auction.proxy.title"/>" data-toggle="tooltip">
				<input type="checkbox" value="1" 
					id="proxy_${auction.auctionCode}" 
					name="proxy_${auction.auctionCode}" 
					class="checkbox" />
				<spring:message code="auction.proxy"/>
				<c:if test="${auction.winner and auction.proxyBid.price.amount gt 0}"> 
					<br />
					<spring:message code="common.currencySymbol"/>&nbsp;
					<fmt:formatNumber value="${auction.proxyBid.price.amount}" 
							groupingUsed="true" 
							minFractionDigits="2" 
							maxFractionDigits="2"/>
				</c:if>
			</label>
		</div>
		
		<div class="span3">
			<button data-auctionCode="${auction.auctionCode}" class="bid btn btn-icon btn-primary glyphicons circle_ok" type="button" title="click this button to place your bid on this item" data-toggle="tooltip">
				<i></i>Bid
			</button>
			<!-- Add class "disabled" after submit -->
			<!-- <button class="btn btn-icon btn-primary glyphicons circle_ok disabled" type="button" title="click this button to place your bid on this item" data-toggle="tooltip">
				<i></i>Bid
			</button> -->
		</div>
	</div>
</div>
<!-- // auctionRow.jsp END -->