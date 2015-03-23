<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<div class="widget">
<div class="widget-head"><h4 class="heading glyphicons charts"><i></i>Winner Report</h4></div>
<div class="widget-body">

<%@include file="dataSource.jsp"%>

<sql:query var="result" dataSource="${dataSource}" sql="select a.auctioncode, a.name as auction, a.auctiontype, 
date_format(a.startdate,'%Y-%m-%d %r') as startdate,
date_format(a.enddate,'%Y-%m-%d %r') as enddate,
if (a.enddate < now(), 'CLOSED', 'RUNNING') as status,
a.baseprice, a.reserveprice, a.deltaprice,
b.price as winning_bid, date_format(b.bidtime,'%Y-%m-%d %r') as bid_time, 
u.username as bidder,
concat(u.firstname, ' ', u.lastname) as bidder_name, u.organization
from bid b
inner join (
    select auction_id, max(sequence) as sequence from bid group by auction_id
) bb on b.auction_id = bb.auction_id and b.sequence = bb.sequence
inner join users u on u.user_id = b.user_id
inner join auction a on a.auction_id = b.auction_id
order by a.startdate desc"/>

<%@include file="results.jsp"%>
</div>
</div>