<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="widget">
<div class="widget-head"><h4 class="heading glyphicons charts"><i></i>Bid Details Report</h4></div>
<div class="widget-body">

<form action="<spring:url value="/report/biddersByRank"/>" method="post">
	<div class="row-fluid">
	<label for="auctionCode" class="span2">Auction Code</label>
	<input type="text" name="auctionCode" id="auctionCode" class="span2">
	<input type="submit" class="btn btn-primary span2" value="Generate">
	</div>
</form>

<%@include file="dataSource.jsp"%>

<c:if test="${not empty auctionCode}">
<sql:query var="result" dataSource="${dataSource}" sql="select u.username, concat(u.firstname, ' ', u.lastname) as bidder, u.organization,
date_format(b.bidtime,'%Y-%m-%d %r') as bid_time, b.price
from auction a
inner join (
    select auction_id, max(sequence) as sequence from bid
    group by auction_id, user_id
) bb on bb.auction_id = a.auction_id
inner join bid b on b.auction_id = bb.auction_id and b.sequence = bb.sequence
inner join users u on u.user_id = b.user_id
where a.auctioncode = '${auctionCode}'
order by b.sequence desc;"/>

<%@include file="results.jsp"%>

</c:if>

</div></div>