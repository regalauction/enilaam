<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="dynamicTable table table-striped table-bordered table-condensed">
	<thead>
  <tr>
    <c:forEach items="${result.columnNames}" var="column">
      <th>${column}</th>
    </c:forEach>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${result.rowsByIndex}" begin="0" var="row">
    <tr>
      <c:forEach items="${row}" var="cell">
        <td>${cell}</td>
      </c:forEach>
    </tr>
  </c:forEach>
  </tbody>
</table>
