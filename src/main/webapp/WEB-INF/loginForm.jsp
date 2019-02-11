<%@ page contentType="text/html; charset=UTF-8" %>
                    <%--
  ~ loginForm.jsp
  ~ This file was last modified at 2019-02-11 21:54 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<!-- form method="post" action="j_security_check" class="loginForm" onsubmit="return checkPassword(this);" -->
                    <form method="post" action="j_security_check" class="loginForm">
                        <div class="w3-row login-margin">
                            <div class="w3-col m1 text-margin">
                                <label for="login">Login: </label>
                            </div>
                            <div class="w3-col m2">
                                <input type="text" id="login" name="j_username" autocomplete="off" placeholder="Type your login"/>
                            </div>
                        </div>
                        <div class="w3-row login-margin">
                            <div class="w3-col m1 text-margin">
                                <label for="password">Password: </label>
                            </div>
                            <div class="w3-col m2">
                                <input type="password" id="password" name="j_password" autocomplete="off" placeholder="Type your password"/>
                            </div>
                        </div>
                        <div class="w3-row login-margin">
                            <div class="w3-col m1 submitArea text-margin">
                                <input type="submit" id="submit" value="Logon"/>
                            </div>
                        </div>
                    </form>
