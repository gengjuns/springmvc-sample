<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="width" required="false" %>
<%@attribute name="labelCode" required="false" %>
<%@attribute name="label" required="false" %>
<%@attribute name="styleId" required="false" %>
<%@attribute name="multiple" required="false"  type="java.lang.Boolean" %>
<%@attribute name="itemValue" required="false" %>
<%@attribute name="expression" required="false"%>
<%@attribute name="path" required="false"%>


<c:if test="${empty width }">
	<c:set var="width" value="span6"/>
</c:if>



<div class="${width}">
        <div class="control-group">
        	<c:choose>
        		<c:when test="${not empty labelCode}">
        			<label class="control-label" for="${path}"><spring:message code="label_${fn:toLowerCase(labelCode)}"/></label>
        		</c:when>
        		<c:when test="${not empty label}">
        			<label class="control-label" for="${path}"><c:out value="${label}"/></label>
        		</c:when>
        	</c:choose>

            <div class="controls">
                
                
                <label class="control-value" id="${styleId }">
					<c:choose>
						<c:when test="${not empty multiple and multiple}">
							<spring:eval expression="${expression}" var="collection"/>
							<ul class="unstyled">
							<c:forEach items="${collection}" var="item">
								<li>
									<c:choose>
										<c:when test="${not empty itemValue}">
											<spring:eval expression="item[itemValue]"/>
										</c:when>
										<c:otherwise>
											<spring:eval expression="item"/>
										</c:otherwise>
									</c:choose>
								</li>
							</c:forEach>
							</ul>
						</c:when>
						
						<c:otherwise>
							<c:choose>
								<c:when test="${not empty itemValue }">
									<spring:eval expression="${expression}" var="property"/>
									<c:if test="${not empty property}">
										<spring:eval expression="property[itemValue]"></spring:eval>
									</c:if>
									<c:if test="${empty property }">
										&nbsp;
									</c:if>
								</c:when>
                                <c:when test="${not empty path}">
                                    <spring:bind path="${path}">
                                        ${status.value}
                                    </spring:bind>
                                </c:when>
								<c:otherwise>
									<spring:eval expression="${expression}"/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</label>
            </div>
        </div>
</div>