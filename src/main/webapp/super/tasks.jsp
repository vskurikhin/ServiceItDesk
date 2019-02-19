<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "su.svn.shared.Constants.Rest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ tasks.jsp
  ~ This file was last modified at 2019-02-16 23:28 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="head-title">Service It Desk «Консоль задач» | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/style-all.min.css'/>
    <style>
        .container { margin:150px auto;}
    </style>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/script-all.min.js'></script>
    <script language="javascript">
        var STATUS_NONE = 0;
        var STATUS_NEW = 1;
        var STATUS_WORK = 2;
        var STATUS_CLOSED = 3;
        var rootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.TASK_RESOURCE %>';
    </script>
</head>

<body class="body">
    <table class="tg body">
        <%@ include file = "menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left-aside" style="border-right: 2px dotted black; height: 600px;">
                <%@ include file = "aside.jsp" %>
            </td>
            <td class="tg-0lax my-right-main">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">
                    <form id="taskForm">
                        <table class="tg2">
                            <tr>
                                <th class="tg2-baqh" style="height: 44px">
                                    <button form="taskForm" id="btnStatus1" hidden>В работу</button>
                                    <button form="taskForm" id="btnAddMessage" hidden>Резолюция</button>
                                    <button form="taskForm" id="btnStatus2" hidden>Решение</button>
                                </th>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Номер:</label>
                                    <br/>
                                    <input form="taskForm" id="taskId" name="id" type="text" disabled title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Заголовок:</label>
                                    <br/>
                                    <input form="taskForm" type="text" id="title" name="title" class="my-text-name"
                                           disabled title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label id="consumer" name="consumer">Инициатор:</label>
                                    <br/>
                                    <br/>
                                    <label id="status" name="status">Статус:</label>
                                    <br/>
                                    <br/>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <label>Описание:</label>
                                            <br/>
                                            <textarea form="taskForm" id="description" name="description"
                                                      class="my-test-area" readonly title=""></textarea>
                                        </div>
                                    </div>
                                    <br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-message-textarea">
                                            <label>Комментарий:</label>
                                            <br/>
                                            <textarea disabled form="taskForm" id="message-text" name="message-text"
                                                      class="my-message-textarea" title=""></textarea>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>История</label>
                                    <div id="div-messages">
                                        <table class="tg0" id="table-messages">
                                            <tr>
                                                <td class="tg0-0lax">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </main>
            </td>
        </tr>
        <%@ include file = "footer.jsp" %>
    </table>
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}/"/>js/coordinators/tasks.js"></script>
    </body>
</html>
