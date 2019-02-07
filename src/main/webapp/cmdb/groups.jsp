<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "su.svn.shared.Constants.Rest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ groups.jsp
  ~ This file was last modified at 2019-02-04 22:58 by Victor N. Skurikhin.
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
    <title id="title">Service It Desk CMDB Console Группы | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/style-all.min.css'/>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/script-all.min.js'></script>
    <script language="javascript">
        var rootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.GROUP_RESOURCE %>'
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
                <main id="main" class="w3-col m12 w3-margin-0 my-right-main">
                    <form id="groupForm">
                        <table class="tg2">
                            <tr>
                                <td class="tg2-baqh" colspan="2">
                                    <button form="groupForm" id="btnAdd">Clear</button>
                                    <button form="groupForm" id="btnSave">Add</button>
                                    <button form="groupForm" id="btnDelete">Delete</button>
                                    <button form="groupForm" id="btnSearch" hidden>Search</button>
                                </td>
                            </tr>
                            <tr>
                                <th class="tg2-0laxl" rowspan="3">
                                    <div class="leftArea">
                                        <ul id="groupList"></ul>
                                    </div>
                                </th>
                                <th class="tg2-0lax">
                                    <label>Id:</label><br/>
                                    <input form="groupForm" id="groupId" name="id" type="text" disabled title=""/>
                                </th>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Name:</label><br/>
                                    <input form="groupForm" type="text" id="name" name="name" required title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Notes:</label><br/>
                                    <textarea form="groupForm" id="description" name="description" class="my-test-area" title="">
                                    </textarea>
                                </td>
                            </tr>
                        </table>
                    </form>
                </main>
            </td>
        </tr>
        <%@ include file = "footer.jsp" %>
    </table>
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}/"/>js/groups.js"></script>
    </body>
</html>
