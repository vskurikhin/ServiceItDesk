<%@ page contentType="text/html; charset=UTF-8" %>

<%--
  ~ aside-cmdb.jsp
  ~ This file was last modified at 2019-02-16 12:42 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
                <aside class = "w3-col w3-margin-0 my-left-aside">
                    <ul>
                        <li><a id="aside-menu-0" href='<c:out value="${baseURL}/"/>super/groups.jsp'>«Группы»</a></li>
                        <li><a id="aside-menu-1" href='<c:out value="${baseURL}/"/>super/users.jsp'>«Пользователи»</a></li>
                        <li><a id="aside-menu-2" href='<c:out value="${baseURL}/"/>super/configuration-types.jsp'>«Типы конфигурационных едениц»</a></li>
                        <li><a id="aside-menu-3" href='<c:out value="${baseURL}/"/>super/configuration-units.jsp'>«Конфигурационные еденицы»</a></li>
                    </ul>
                </aside>
