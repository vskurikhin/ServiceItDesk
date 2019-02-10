<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "su.svn.shared.Constants.Rest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ edit.jsp
  ~ This file was last modified at 2019-02-10 22:33 by Victor N. Skurikhin.
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
    <title id="head-title">Service It Desk CMDB Console Конфигурационные еденицы | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/style-all.min.css'/>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/jquery.dropdown.css' />
    <style>
        .container { margin:150px auto;}
    </style>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/script-all.min.js'></script>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/jquery.dropdown.js'></script>
    <script language="javascript">
        var rootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.TASK_RESOURCE %>';
        var userRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.USER_RESOURCE %>';
        var statusRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.STATUS_RESOURCE %>';
        var messageRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.MESSAGE_RESOURCE %>';
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
                                <td class="tg2-baqh" colspan="2" style="height: 44px">
                                    <button form="taskForm" id="btnAdd">Clear</button>
                                    <button form="taskForm" id="btnSave">Add</button>
                                    <button form="taskForm" id="btnDelete">Delete</button>
                                </td>
                            </tr>
                            <tr>
                                <th class="tg2-0laxl" rowspan="3">
                                    <div class="leftArea">
                                        <ul id="taskList"></ul>
                                    </div>
                                </th>
                                <th class="tg2-0lax">
                                    <label>Номер:</label>
                                    <br/>
                                    <input form="taskForm" id="taskId" name="id" type="text" disabled title=""/>
                                </th>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Заголовок:</label>
                                    <br/>
                                    <input form="taskForm" type="text" id="title" name="title" required title=""/>
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
                                    <label>Статус:</label>
                                    <br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-dropdown-sin-2">
                                            <div class="dropdown-sin-2 dropdown-single">
                                                <select form="taskForm" id="status" name="status" style="display:none"
                                                        placeholder="Select">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
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
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}/"/>js/task-edit.js"></script>
    </body>
</html>
