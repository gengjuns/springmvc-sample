<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="path" required="true" %>
<%@attribute name="width" required="false" %>
<%@attribute name="controlStyle" required="false" %>
<%@attribute name="labelCode"%>
<%@attribute name="label"%>
<%@attribute name="required" required="false" %>
<%@attribute name="helpText"%>

<c:if test="${empty width }">
	<c:set var="width" value="span6"/>
</c:if>
<c:if test="${empty controlStyle}">
    <c:set var="controlStyle" value="controls"/>
</c:if>



<div class="${width}">
    <spring:bind path="${path}">
        <div class="control-group ${status.error ? 'error' : ''}">
        	<c:choose>
        		<c:when test="${not empty labelCode}">
        			<label class="control-label" for="${path}"><c:if test="${required}"><span class="required"><spring:message code="msg_mandatory_symbol" htmlEscape="false"/></span></c:if><spring:message code="label_${fn:toLowerCase(labelCode)}"/></label>
        		</c:when>
        		<c:when test="${not empty label}">
        			<label class="control-label" for="${path}"><c:if test="${required}"><span class="required"><spring:message code="msg_mandatory_symbol" htmlEscape="false"/></span></c:if><c:out value="${label}"/></label>
        		</c:when>
        	</c:choose>


            <div class="${controlStyle}">
                <jsp:doBody/>
                	<c:if test="${status.error}">
                    <span class="help-inline"><form:errors path="${path }" /></span>
                    </c:if>
                    <c:if test="${not empty helpText}">
                    <span class="help-block">${helpText}</span>
                    </c:if>
            </div>
        </div>
    </spring:bind>
</div>