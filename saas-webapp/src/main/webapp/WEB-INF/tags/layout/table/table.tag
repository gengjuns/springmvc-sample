<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="data" required="true" type="java.util.Collection"%>
<%@attribute name="styleId" required="true"%>
<%@attribute name="width" required="false"%>

<c:if test="${empty width}">
	<c:set var="width" value="span12"/>
</c:if>

    <c:set var="columnProperties" scope="request"/>
    <c:set var="columnLabels" scope="request"/>
    <jsp:doBody/>
    <div class="${width} k-content">
    <table id="${styleId}" data-type="table">
      <thead>
        <tr>
          <c:forTokens items="${columnLabels}" delims="${'&#9999;'}" var="columnHeading">
            <th>
              <c:out value="${columnHeading}" />
            </th>
          </c:forTokens>
          <th>
           Action
          </th>
        </tr>
      </thead>
      
      <c:forEach items="${data}" var="item">
        <tr>
        	<c:set var="itemId">
            	<spring:eval expression="item['id']" />
            </c:set>
          <c:forTokens items="${columnProperties}" delims="${'&#9999;'}" var="column" varStatus="num">
            <td><spring:eval expression="item[column]" /></td>
            
          </c:forTokens>
          <td>
          	<a class="btn row-edit icon-edit" href="#" row_id="${itemId}" data-toggle='tooltip' title="edit"/></a>
          	<a class="btn row-delete icon-trash" href="#" row_id="${itemId}" data-toggle='tooltip' title='delete'></a>
          </td>
        </tr>
      </c:forEach>
    </table>
    </div>
    
