<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "su.svn.shared.Constants.Rest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ new-task.jsp
  ~ This file was last modified at 2019-02-16 22:30 by Victor N. Skurikhin.
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
    <title id="head-title">Service It Desk «Новая задача» | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/style-all.min.css'/>
    <style>
        .container { margin:150px auto;}
    </style>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/script-all.min.js'></script>
    <script language="javascript">
        var STATUS_NEW = 1;
        var rootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.TASK_RESOURCE %>';
        var userRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.USER_RESOURCE %>';
        var statusRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.STATUS_RESOURCE %>';
    </script>
</head>

<body class="body">
    <table class="tg body">
        <%@ include file = "menu.jsp" %>
        <tr>
            <td class="tg-0lax" style="height: 600px;">
                <%@ include file = "aside.jsp" %>
            </td>
            <td class="tg-0lax my-right-main" >
                <main id="main" class="w3-col w3-margin-0 my-left">
                    <form id="taskForm">
                        <table class="tg2" style="width: 60%">
                            <tr>
                                <th class="tg2-baqh" style="height: 44px">
                                    <button form="taskForm" id="btnAdd">Создать</button>
                                </th>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <input hidden form="taskForm" id="taskId" name="id" type="text" disabled title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Заголовок:</label>
                                    <br/>
                                    <input form="taskForm" type="text" id="title" name="title" style="width: 100%;" required title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Инициатор:</label>
                                    <br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-dropdown-sin-1">
                                            <div class="dropdown-sin-1 dropdown-single">
                                                <select form="taskForm" id="consumer" name="consumer"
                                                        style="display:none" placeholder="Select">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <br/>
                                    <label id="status" name="status">Статус:</label>
                                    <br/>
                                    <br/>
                                    <div class="row">
                                        <div class="col-sm-4">
                                            <label>Комментарий:</label>
                                            <br/>
                                            <textarea form="taskForm" id="description" name="description"
                                                      class="my-test-area" title=""></textarea>
                                        </div>
                                    </div>
                                    <br/>
                                    <br/>
                                </td>
                            </tr>
                        </table>
                    </form>
                </main>
            </td>
        </tr>
        <%@ include file = "footer.jsp" %>
    </table>
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}/"/>js/actuaries/tasks.js"></script>
    </body>
</html>
