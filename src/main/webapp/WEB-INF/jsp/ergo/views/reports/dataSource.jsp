<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<spring:theme code="jdbc.driverClass" var="driverClass"/>
<spring:theme code="jdbc.url" var="url"/>
<spring:theme code="jdbc.username" var="username"/>
<spring:theme code="jdbc.password" var="password"/>

<sql:setDataSource var="dataSource" driver="${driverClass}" url="${url}" user="${username}" password="${password}"/>