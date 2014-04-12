<!-- gallery.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div id="gallery" class="carousel slide">
	<ol class="carousel-indicators">
		<c:forEach items="${images}" varStatus="status">
			<li data-target="#gallery" data-slide-to="${status.count}" class="${status.first? 'active' : ''}"></li>
		</c:forEach>
	</ol>
	<!-- Carousel items -->
	<div class="carousel-inner">
		<c:forEach var="image" items="${images}" varStatus="status">
			<spring:url value="/attachments/images/{filename}" var="imageUrl">
				<spring:param name="filename" value="${image.code}"/>
			</spring:url>
			<div class="item ${status.first? 'active' : ''}">
				<img alt="${image.name}" src="${imageUrl}"/>
			</div>
		</c:forEach>
	</div>
	<!-- Carousel nav -->
	<a class="carousel-control left" href="#gallery" data-slide="prev">&lsaquo;</a>
	<a class="carousel-control right" href="#gallery" data-slide="next">&rsaquo;</a>
</div>
<!-- // gallery.jsp END -->