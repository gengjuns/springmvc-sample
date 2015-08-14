<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<layout:page>
    <c:set value="${requestScope.statistics}" var="statistics" target="org.hibernate.stat.Statistics"/>
    <form class="form-horizontal">
        <fieldset>
        <ul class="nav nav-tabs">
            <li class="active"><a href="#basic" data-toggle="tab">Basic</a></li>
            <li><a href="#2ndLevel" data-toggle="tab">Second Level Cache</a></li>
            <li><a href="#entity" data-toggle="tab">Entity Statistics</a></li>
            <li><a href="#query" data-toggle="tab">Query Statistics</a></li>
            <li><a href="#CacheManagement" data-toggle="tab">Cache Management</a></li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="basic">
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Session Open</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.sessionOpenCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Session Close</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.sessionCloseCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Flush Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.flushCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Connect Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.connectCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Prepared Statement</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.prepareStatementCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Close Statement</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.closeStatementCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Load Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.entityLoadCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Update Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.entityUpdateCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Insert Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.entityInsertCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Delete Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.entityDeleteCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Fetch Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.entityFetchCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Collection Load Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.collectionLoadCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Collection Update Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.collectionUpdateCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Collection Remove Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.collectionRemoveCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Collection Recreate Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.collectionRecreateCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Collection Fetch Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.collectionFetchCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">2nd Level Hit</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.secondLevelCacheHitCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">2nd Level Miss</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.secondLevelCacheMissCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">2nd Level Put</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.secondLevelCachePutCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Natural Id Hit</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.naturalIdCacheHitCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Natural Id Miss</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.naturalIdCacheMissCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Natural Id Put</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.naturalIdCachePutCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Natural Id Exe Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.naturalIdQueryExecutionCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Natural Id Exe Max Time</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.naturalIdQueryExecutionMaxTime}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Query Exe Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.queryExecutionCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Query Exe Max Time</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.naturalIdQueryExecutionMaxTime}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Query Cache Hit Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.queryCacheHitCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Query Cache Miss Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.queryCacheMissCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Query Cache Put Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.queryCachePutCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">TS Cache Hit Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.updateTimestampsCacheHitCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">TS Cache Miss Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.updateTimestampsCacheMissCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">TS Cache Put Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.updateTimestampsCachePutCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
                <layout:row>
                    <div class="control-group span3">
                        <label class="control-label">Transaction Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.transactionCount}
                            </label>
                        </div>
                    </div>
                    <div class="control-group span3">
                        <label class="control-label">Optimistic Failure Count</label>

                        <div class="controls">
                            <label class="control-value">
                                    ${statistics.optimisticFailureCount}
                            </label>
                        </div>
                    </div>
                </layout:row>
            </div>
            <div class="tab-pane" id="2ndLevel">
                <c:forEach items="${statistics.secondLevelCacheRegionNames}" var="regionName" varStatus="status">
                    <c:if test="${status.count % 3 == 0}">
                        <div class="row-fluid">
                    </c:if>
                        <div class="control-group span4">
                            <label class="control-label">
                                <div style="word-wrap: break-word">
                                        ${regionName}
                                </div>
                            </label>

                            <div class="controls">
                                <label class="control-value">
                                    <c:set var="cache" value="${statistics.getSecondLevelCacheStatistics(regionName)}"/>
                                    Hit Count: ${cache.hitCount}<br/>
                                    Miss Count: ${cache.missCount}<br/>
                                    Put Count: ${cache.putCount}<br/>
                                    Memory: ${cache.elementCountInMemory}<br/>
                                    Disk: ${cache.elementCountOnDisk}<br/>
                                </label>
                            </div>
                        </div>
                    <c:if test="${status.count % 3 == 0}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="tab-pane" id="entity">
                <c:forEach items="${statistics.secondLevelCacheRegionNames}" var="regionName" varStatus="status">
                    <c:if test="${status.count % 3 == 0}">
                        <div class="row-fluid">
                    </c:if>
                    <div class="control-group span4">
                        <label class="control-label">
                            <div style="word-wrap: break-word">
                                    ${regionName}
                            </div>
                        </label>

                        <div class="controls">
                            <label class="control-value">
                                <c:set var="stats" value="${statistics.getEntityStatistics(regionName)}"/>
                                Delete Count: ${stats.deleteCount}<br/>
                                Insert Count: ${stats.insertCount}<br/>
                                Load Count: ${stats.loadCount}<br/>
                                Update Count: ${stats.updateCount}<br/>
                                Fetch Count: ${stats.fetchCount}<br/>
                                Optimistic Failure Count: ${stats.optimisticFailureCount}<br/>
                            </label>
                        </div>
                    </div>
                    <c:if test="${status.count % 3 == 0}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <div class="tab-pane" id="query">
                <c:forEach items="${statistics.queries}" var="query" varStatus="status">
                    <c:if test="${status.count % 3 == 0}">
                        <div class="row-fluid">
                    </c:if>
                    <div class="control-group span4">
                        <label class="control-label">
                            <div style="word-wrap: break-word">
                                ${query}
                            </div>
                        </label>

                        <div class="controls">
                            <label class="control-value">
                                <c:set var="stats" value="${statistics.getQueryStatistics(query)}"/>
                                Execute Count: ${stats.executionCount}<br/>
                                Cache Hit Count: ${stats.cacheHitCount}<br/>
                                Cache Put Count: ${stats.cachePutCount}<br/>
                                Cache Miss Count: ${stats.cacheMissCount}<br/>
                                Cache Exe Row Count: ${stats.executionRowCount}<br/>
                                Exe Avg Time: ${stats.executionAvgTime}<br/>
                                Exe Max Time: ${stats.executionMaxTime}<br/>
                                Exe Min Time: ${stats.executionMinTime}<br/>
                            </label>
                        </div>
                    </div>
                    <c:if test="${status.count % 3 == 0}">
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            
            <div class="tab-pane" id="CacheManagement">
               <a href="${pageContext.request.contextPath}/statistics/flush/aa">Flush AA resource</a>
            </div>
        </div>
        </fieldset>
    </form>
</layout:page>
