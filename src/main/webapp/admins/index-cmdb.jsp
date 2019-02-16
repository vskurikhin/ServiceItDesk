<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ index-cmdb.jsp
  ~ This file was last modified at 2019-02-16 13:44 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Service It Desk CMDB Console | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href="<c:out value="${baseURL}"/>css/style-all.min.css"/>
</head>

<body class="body">
    <table class="tg body">
<%@ include file = "menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left-aside" style="border-right: 2px dotted black; height: 600px;">
                <%@ include file = "aside-cmdb.jsp" %>
            </td>
            <td class="tg-0lax my-right-main">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">
                    <div id="main-container">
                        <div class="w3-margin">
                            <h3>Управление конфигурациями</h3>
                            <p style="text-align: justify">
                                CMDB занимает важное место для процессов, связанных с поддержкой ИТ-услуг. К основным функциям CMDB в соответствии с
                                ITIL относятся: хранение информации по объектам ИТ-инфраструктуры в форме конфигурационных единиц (КЕ); поддержка
                                связей между КЕ, связей КЕ с инцидентами, проблемами, изменениями и релизами; контроль версий КЕ; поддержка
                                «базовых линий» КЕ и «снимков состояния» CMDB. Все это позволяет получить ряд преимуществ, ключевыми из которых
                                являются: улучшение отчетности по ИТ-инфраструктуре, сокращение сроков устранения инцидентов и сокращение количества
                                сбоев ИТ.
                            </p>
                        </div>
                    </div>
                </main>
            </td>
        </tr>
<%@ include file = "footer.jsp" %>
    </table>
</body>
</html>
