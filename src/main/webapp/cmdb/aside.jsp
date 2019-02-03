<%@ page contentType="text/html; charset=UTF-8" %>
<%--
  ~ aside.jsp
  ~ This file was last modified at 2019-02-03 20:54 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
                <aside class = "w3-col w3-margin-0 my-right my-main-aside">
                    <ul>
                        <li><a id="aside-menu-0" href="<c:out value="${baseURL}/"/>welcome.jsp">«IT Home Page»</a></li>
                        <li><a id="aside-menu-1" href="<c:out value="${baseURL}/"/>incedent/index.jsp">«Incedent Console»</a></li>
                        <li><a id="aside-menu-2" href="<c:out value="${baseURL}/"/>task/index.jsp">«Task Console»</a></li>
                        <li><a id="aside-menu-3" href="<c:out value="${baseURL}/"/>cmdb/index.jsp">«CMDB Console»</a></li>
                    </ul>
                </aside>
