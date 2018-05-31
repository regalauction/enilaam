<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="separator"></div>
<div class="wrapper">
	<div class="container-960">
			<div class="wizard">
				<form:form modelAttribute="spotDealPurchaseForm">
				
				<form:hidden path="quantity"/>
				
				<div class="widget widget-tabs widget-tabs-double">
					<!-- Widget heading -->
					<div class="widget-head">
						<h4 class="heading glyphicons flash">
							<i></i>
							<spring:message code="spotdeal.heading"/>: <c:out value="${spotDealPurchaseForm.name}" />
							<form:hidden path="name"/>
						</h4>
					</div>
					<!-- // Widget heading END -->
					
					<div class="widget-body">
						<form:errors path="*" element="div" cssClass="alert alert-error"/>
						
						<form:hidden path="code"/>
						
						<dl class="dl-horizontal">
						
							<dt>Seller</dt>
							<dd>
								<c:out value="${spotDealPurchaseForm.sellerName}" />
								<form:hidden path="sellerName"/>
								<form:hidden path="sellerEmail"/>
							</dd>
							
							<dt>Basic Price</dt>
							<dd>
								<form:hidden path="basePrice"/>
								<spring:message code="common.currencySymbol" />
								&nbsp;
								<fmt:formatNumber value="${spotDealPurchaseForm.basePrice}"
									groupingUsed="true" minFractionDigits="2"
									maxFractionDigits="2" />
							</dd>

							<dt>UOM</dt>
							<dd>
								<form:hidden path="unitOfMeasure"/>
								<c:out value="${spotDealPurchaseForm.unitOfMeasure}" />
							</dd>

							<dt>Tentative Specification</dt>
							<dd>
								<form:hidden path="spec"/>
								<c:out value="${spotDealPurchaseForm.spec}" />
							</dd>
							
							<dt>Delivery Period</dt>
							<dd>
								<form:hidden path="deliveryPeriod"/>
								<c:out value="${spotDealPurchaseForm.deliveryPeriod}" />
							</dd>
							
							<dt>Address</dt>
							<dd>
								<form:hidden path="address"/>
								<c:out value="${spotDealPurchaseForm.address}" />
							</dd>
							
							<dt>City</dt>
							<dd>
								<form:hidden path="city"/>
								<c:out value="${spotDealPurchaseForm.city}" />
							</dd>
							
							<dt>State</dt>
							<dd>
								<form:hidden path="state"/>
								<c:out value="${spotDealPurchaseForm.state}" />
							</dd>
						
							<dt>PIN</dt>
							<dd>
								<form:hidden path="pincode"/>
								<c:out value="${spotDealPurchaseForm.pincode}" />
							</dd>
						</dl>
						
						<spring:bind path="orderQuantity">
							<div class="control-group ${status.error ? 'error' : ''}">
								<form:label path="orderQuantity" cssClass="control-label"><spring:message code="spotdeal.orderquantity"/></form:label>
							</div>
							<div class="controls">
								<form:input path="orderQuantity" />
								<c:if test="${status.error}">
									<p class="error help-block"><form:errors cssClass="label label-important" path="orderQuantity"/></p>
								</c:if>
							</div>
						</spring:bind>	
						
					</div>
					
					<div class="right form-actions">
						<button type="submit" class="btn btn-primary btn-icon glyphicons circle_ok" onclick="return confirm('Are you sure you want to buy this item?');"><i></i>Buy</button>
					</div>
				</div>
				</form:form>
			</div>
			
			
	</div>
</div>