<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "su.svn.shared.Constants.Rest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ users.jsp
  ~ This file was last modified at 2019-02-16 23:11 by Victor N. Skurikhin.
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
    <title id="title">Service It Desk CMDB Console Пользователи | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/style-all.min.css'/>
    <style>
        .container { margin:150px auto;}
    </style>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/script-all.min.js'></script>
    <script language="javascript">
        let rootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.USER_RESOURCE %>';
        let groupRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.GROUP_RESOURCE %>'
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
                    <form id="userForm">
                        <table class="tg2">
                            <tr>
                                <th class="tg2-baqh" colspan="2" style="height: 44px">
                                    <button form="userForm" id="btnAdd">Clear</button>
                                    <button form="userForm" id="btnSave">Add</button>
                                    <button form="userForm" id="btnDelete">Delete</button>
                                    <button form="userForm" id="btnSearch" hidden>Search</button>
                                </th>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Id:</label><br/>
                                    <input form="userForm" id="userId" name="id" type="text" disabled title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Name:</label><br/>
                                    <input form="userForm" type="text" id="name" name="name" class="my-text-name" required title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Group:</label><br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-dropdown-sin-1">
                                          <div class="dropdown-sin-1 dropdown-single">
                                            <select form="userForm" id="group" name="group" style="display:none" placeholder="Select">
                                            </select>
                                          </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                      <div class="col-sm-4">
                                        <label>Notes:</label><br/>
                                        <textarea form="userForm" id="description" name="description" class="my-test-area" title=""></textarea>
                                      </div>
                                    </div>
                                    <br>
                                    <br>
                                </td>
                            </tr>
                        </table>
                    </form>
                </main>
            </td>
        </tr>
        <%@ include file = "footer.jsp" %>
    </table>
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}/"/>js/cmdbs/users.js"></script>
    </body>
</html>
