<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<div class="widget">
	<div class="widget-head">
		<h4 class="heading glyphicons flash">
			<i></i>
			<spring:message code="info.spotdeals.heading" /> (<c:out value="${fn:length(spotDeals)}"/>)			
		</h4>
	</div>
	<div class="widget-body">
		<div id="news">
			<c:choose>
				<c:when test="${empty spotDeals}">
					No spot deals.
				</c:when>
				<c:otherwise>
					<ul>
						<c:forEach items="${spotDeals}" var="spotDealItem">						
							<li class="spotdeal-item">
								<div class="row-fluid">
									<div class="span4">
										<c:forEach items="${spotDealItem.images}" var="image">
											<spring:url value="/attachments/images/{filename}"
												var="imageUrl">
												<spring:param name="filename" value="${image.code}" />
											</spring:url>
											<img alt="${image.name}" src="${imageUrl}" height="100"
												width="100" />
										</c:forEach>
									</div>
									<div class="span8">
										<h5>
											<c:out value="${spotDealItem.name}" />
										</h5>
										<dl class="dl-horizontal">
											<dt>Basic Price</dt>
											<dd>
												<spring:message code="common.currencySymbol" />
												&nbsp;
												<fmt:formatNumber value="${spotDealItem.basePrice.amount}"
													groupingUsed="true" minFractionDigits="2"
													maxFractionDigits="2" />
											</dd>

											<dt>UOM</dt>
											<dd>
												<c:out value="${spotDealItem.unitOfMeasure}" />
											</dd>

											<c:if test="${spotDealItem.spec}">
												<dt>Tentative Specification</dt>
												<dd>
													<c:out value="${spotDealItem.spec}" />
												</dd>
											</c:if>

										</dl>
										
										<security:authorize ifAnyGranted="ROLE_BIDDER">
											<spring:url value="/spotdeals/purchase/${spotDealItem.code}" var="purchaseUrl"/>
											<a class="purchase btn btn-primary" href="${purchaseUrl}">Buy</a>
										</security:authorize>
										<security:authorize ifNotGranted="ROLE_BIDDER">
											<a class="purchase btn" data-toggle="modal" data-target="#loginAlertModal" href="javascript: void(0);">Buy!</a>
										</security:authorize>
									</div>
								</div>
							</li>
						</c:forEach>
					</ul>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
	<div class="widget-footer">
		<a id="news_next" class="glyphicons step_forward"
			href="javascript: void(0);"><i></i></a> <a id="news_prev"
			class="glyphicons step_backward" href="javascript: void(0);"><i></i></a>
	</div>
</div>



<!-- Login alert -->
<div class="modal hide fade" id="loginAlertModal">
	<div class="modal-dialog">
		<div class="modal-content">
			  <div class="modal-header">
		      	<button type="button" class="close" data-dismiss="modal">&times;</button>
		      	<h4 class="modal-title glyphicons chevron-right"><i></i> Login Required!</h4>
		      </div>
			<div class="modal-body">
				<p>You need to be logged in to buy the spot deal! Please <a href="<spring:url value="/"/>">login</a> or register.</p>
			</div>
			<div class="modal-footer">
	        	<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	     	</div>
		</div>
	</div>
</div>
