<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="/WEB-INF/tlds/util.tld" prefix="util"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom"%>
<script type="text/javascript">
<!--
function spinUp(control, amount) {
	var value = $("input[name='" + control + "']").val();
	var newValue = Number(value) + Number(amount);
	$("input[name='" + control + "']").val(newValue);
}
function spinDown(control, amount) {
	var value = $("input[name='" + control + "']").val();
	var newValue = Number(value) - Number(amount);
	$("input[name='" + control + "']").val(newValue);
}
//-->
</script>
<form:form modelAttribute="bidForm">
<spring:hasBindErrors name="bidForm">
	<div class="error"><spring:message code="form.validation.errors"/></div>
</spring:hasBindErrors>
<c:choose>
	<c:when test="${empty auctions}">
		<spring:message code="auction.notfound" />
	</c:when>
	<c:otherwise>
		<c:forEach items="${auctions}" var="auction" varStatus="status">
			<div class="span-23 box">
				<security:authorize ifAnyGranted="ROLE_BIDDER">
					<div class="span-1 prepend-top"><form:checkbox path="auctionCodes" value="${auction.auctionCode}" cssErrorClass="form-error-field"/></div>
				</security:authorize>
				<div class="span-3">
					<c:choose>
						<c:when test="${not empty auction.thumbnail}">
							<img alt="<spring:message code="thumbnail.alt" arguments="${auction.item.name}"/>" 
								src="<spring:url value="/attachments/images/${auction.thumbnail}"/>"
								height="<spring:theme code="item.thumbnail.height" />"
								width="<spring:theme code="item.thumbnail.width" />"/><br />
						</c:when>
						<c:otherwise>No Image</c:otherwise>
					</c:choose>
				</div>
				<div class="span-4">
					<h4><c:out value="${auction.name}" /></h4>
					<c:if test="${not empty auction.documents}">
						<div>
							<ul>
								<c:forEach items="${auction.documents}" var="document">
									<li><a href="<spring:url value="/attachments/documents/${document.code}"/>"><c:out value="${document.name}"/></a></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</div>
				<div class="span-4">
					<div class="span-2 loud"><spring:message code="auction.baseprice" /></div><div class="span-2 last" style="text-align: right;"><span class="WebRupee">Rs.</span><c:out value="${auction.basePrice}" /></div>
					<div class="span-2 loud"><spring:message code="auction.deltaprice" /></div><div class="span-2 last" style="text-align: right;"><span class="WebRupee">Rs.</span><c:out value="${auction.deltaPrice}" /></div>
					<security:authorize ifAnyGranted="ROLE_BIDDER">
						<c:if test="${!auction.noBid}">
							<div class="span-2"><strong><spring:message code="auction.leadprice" /></strong></div><div class="span-2 last highlight" style="text-align: right;"><strong><span class="WebRupee">Rs.</span><c:out value="${auction.leadPrice}" /></strong></div>
						</c:if>
					</security:authorize>
				</div>
				<div class="span-5 border">
					<div class="span-1 loud"><spring:message code="auction.startdate" /></div><div class="span-4 last"><joda:format value="${auction.startDate}" style="MM" /></div>
					<div class="span-1 loud"><spring:message code="auction.enddate" /></div><div class="span-4 last"><joda:format value="${auction.endDate}" style="MM" /></div>
					<div class="span-4 prepend-1 loud"><util:period date="${auction.endDate}" /> <spring:message code="auction.timeleft" /></div>
				</div>
				<security:authorize ifAnyGranted="ROLE_BIDDER">
					<spring:bind path="auctions[${auction.auctionCode}].bidPrice">
						<div class="span-6 last">
								<div class="span-1" style="text-align: right; font-size: 200%;"><span class="WebRupee">Rs.</span></div>
								<c:choose>
									<c:when test="${empty status.errorMessage}"><c:set var="nextBidPrice" value="${auction.nextBidPrice}"/></c:when>
									<c:otherwise><c:set var="nextBidPrice" value="${status.value}"/></c:otherwise>
								</c:choose>
								<div class="span-3">
									<input type="text" name="${status.expression}" value="${nextBidPrice}" class="span-3 title <c:if test="${status.error}">form-error-field</c:if>" style="text-align: right;"/>
									<br />
									<form:checkbox path="auctions[${auction.auctionCode}].proxy" label=" Proxy"/>
								</div>
								<div class="span-1">
									<a class="button" href="javascript: spinUp('${status.expression}', ${auction.deltaPrice});">
										<img src="<spring:theme code="icon.button.plus"/>" alt=""/>
									</a>
									<a class="button" href="javascript: spinDown('${status.expression}', ${auction.deltaPrice});">
										<img src="<spring:theme code="icon.button.minus"/>" alt=""/>
									</a>
								</div>
								<div class="span-1 prepend-top last">
									<c:if test="${auction.winner}">
										<img src="<spring:theme code="image.auction.winning"/>" alt="<spring:message code="auction.winning"/>"/>
									</c:if>
								</div>
								<div class="span-5 prepend-1" style="font-style: italic;">
									<form:errors path="${status.expression}"/>
									<spring:message code="${bidResultMap[auction.auctionCode].code}" text=""/>
								</div>
						</div>
					</spring:bind>
				</security:authorize>
				<security:authorize ifAnyGranted="ROLE_OBSERVER">
					<div class="span-7 last large loud">
						<c:choose>
							<c:when test="${auction.noBid}"><spring:message code="auction.nobids"/></c:when>
							<c:otherwise>
								<div class="prepend-1 span-3"><spring:message code="auction.leadprice" /></div>
								<div class="span-3 last" style="text-align: right;"><span class="WebRupee">Rs.</span><c:out value="${auction.leadPrice}" /></div>
								<div class="prepend-1 span-3"><spring:message code="auction.bidcount" /></div>
								<div class="span-3 last" style="text-align: right;"><c:out value="${auction.numBids}" /></div>
							</c:otherwise>
						</c:choose>
					</div>
				</security:authorize>
			</div>
		</c:forEach>
		<form:errors path="auctionCodes" cssClass="error"/>
		<div class="span-24 prepend-top">
			<security:authorize ifAnyGranted="ROLE_BIDDER">
				<custom:button type="submit" labelCode="auction.bid" imgCode="icon.button.bid"/>
			</security:authorize>
			<spring:url value="/auction/bid" var="refreshUrl"/>
			<custom:button type="link" url="${refreshUrl}" labelCode="auction.refresh" imgCode="icon.button.refresh"/>
		</div>
	</c:otherwise>
</c:choose>
</form:form>