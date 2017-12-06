<%-- 
    Document   : createAccount
    Created on : Apr 17, 2015, 5:19:08 PM
    Author     : kevinholland
--%>

<%@page import="com.look.MenuBar.Items"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.RecentFeed" %>
<%@page import="com.look.MenuBar"%>
<%@include file="modal-dialogs.jsp"%>

<!DOCTYPE html>
<%
    if (request.getParameter("username") != null) {
        request.setAttribute("username", request.getParameter("username"));
    }
    if (request.getParameter("firstName") != null) {
        request.setAttribute("firstName", request.getParameter("firstName"));
    }
    if (request.getParameter("lastName") != null) {
        request.setAttribute("lastName", request.getParameter("lastName"));
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <title>Look! Registration</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>
        
        <%out.println(MenuBar.generateMenuBar(session, Items.USER));%>

        <form action="createUser" method="post">
            <div class="row">
                <div class="panel large-6 large-offset-3 medium-8 medium-offset-2 small-12 columns login-box">
                    <div class="row" style="text-align: center">
                        <h1>Register</h1>
                    </div>
                    <div class="row">
                        <label>
                            <input type="text" name="username" placeholder="Username" value="${requestScope.username}"> 
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="password" name="password" placeholder="Password">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="password" name="repeatPassword" placeholder="Repeat Password">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="text" name="firstName" placeholder="First Name" value="${requestScope.firstName}">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="text" name="lastName" placeholder="Last Name" value="${requestScope.lastName}">
                        </label>
                    </div>
                    <div class="row" style="text-align: center;">
                        <p style="color: red;">
                            <%
                                if (request.getAttribute("message") != null) {
                                    out.print(request.getAttribute("message"));
                                }
                            %>
                        </p>
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Register" class="button"> 
                    </div>
                </div>
            </div>
        </form>

        <script src="js/vendor/jquery.js"></script>
        <script src="js/foundation.min.js"></script>
        <script>
            $(document).foundation();
        </script>
    </body>
</html>
