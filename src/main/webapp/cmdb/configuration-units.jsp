<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "su.svn.shared.Constants.Rest" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ configuration-units.jsp
  ~ This file was last modified at 2019-02-10 14:08 by Victor N. Skurikhin.
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
    <title id="title">Service It Desk CMDB Console Конфигурационные еденицы | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/style-all.min.css'/>
    <link rel="stylesheet" href='<c:out value="${baseURL}/"/>css/jquery.dropdown.css' />
    <style>
        .container { margin:150px auto;}
    </style>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/script-all.min.js'></script>
    <script type="text/javascript" language="javascript" src='<c:out value="${baseURL}/"/>js/jquery.dropdown.js'></script>
    <script language="javascript">
        var rootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.CONFIGURATION_UNIT_RESOURCE %>';
        var groupRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.GROUP_RESOURCE %>';
        var userRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.USER_RESOURCE %>';
        var configurationTypeRootURL = '<c:out value="${baseURL}" /><%= Rest.API_URL + "/v1" + Rest.CONFIGURATION_TYPE_RESOURCE %>'
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
                    <form id="configurationUnitForm">
                        <table class="tg2">
                            <tr>
                                <td class="tg2-baqh" colspan="2" style="height: 44px">
                                    <button form="configurationUnitForm" id="btnAdd">Clear</button>
                                    <button form="configurationUnitForm" id="btnSave">Add</button>
                                    <button form="configurationUnitForm" id="btnDelete">Delete</button>
                                    <button form="configurationUnitForm" id="btnSearch" hidden>Search</button>
                                </td>
                            </tr>
                            <tr>
                                <th class="tg2-0laxl" rowspan="3">
                                    <div class="leftArea">
                                        <ul id="configurationUnitList"></ul>
                                    </div>
                                </th>
                                <th class="tg2-0lax">
                                    <label>Id:</label><br/>
                                    <input form="configurationUnitForm" id="configurationUnitId" name="id" type="text" disabled title=""/>
                                </th>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Name:</label><br/>
                                    <input form="configurationUnitForm" type="text" id="name" name="name" class="my-text-name" required title=""/>
                                </td>
                            </tr>
                            <tr>
                                <td class="tg2-0lax">
                                    <label>Admin:</label><br/>
                                    <div class="row">
                                        <div class="col-sm-4"  id="div-dropdown-sin-1">
                                            <div class="dropdown-sin-1 dropdown-single">
                                                <select form="configurationUnitForm" id="admin" name="admin" style="display:none" placeholder="Select">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <label>Owner:</label><br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-dropdown-sin-2">
                                            <div class="dropdown-sin-2 dropdown-single">
                                                <select form="configurationUnitForm" id="owner" name="owner" style="display:none" placeholder="Select">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <label>Group:</label><br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-dropdown-sin-3">
                                          <div class="dropdown-sin-3 dropdown-single">
                                            <select form="configurationUnitForm" id="group" name="group" style="display:none" placeholder="Select">
                                            </select>
                                          </div>
                                        </div>
                                    </div>
                                    <br>
                                    <label>Configuration Type:</label><br/>
                                    <div class="row">
                                        <div class="col-sm-4" id="div-dropdown-sin-4">
                                            <div class="dropdown-sin-4 dropdown-single">
                                                <select form="configurationUnitForm" id="ctype" name="ctype" style="display:none" placeholder="Select">
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                      <div class="col-sm-4">
                                        <label>Notes:</label><br/>
                                        <textarea form="configurationUnitForm" id="description" name="description" class="my-test-area" title=""></textarea>
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
    <script type="text/javascript" language="javascript" src="<c:out value="${baseURL}/"/>js/configuration-units.js"></script>
    </body>
</html>
