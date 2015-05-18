<%-- 
    Document   : login
    Created on : Apr 17, 2015, 3:38:54 PM
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
    if (session.getAttribute("user") != null) {
        response.sendRedirect(".");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Login</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>

        <%out.println(MenuBar.generateMenuBar(session, Items.USER));%>

        <form action="authorizeLogin" method="post">
            <div class="row">
                <div class="panel large-4 large-offset-4 medium-6 medium-offset-3 small-12 columns login-box">
                    <div class="row" style="text-align: center">
                        <h4>Already have an account? Log in here.</h4>
                    </div>
                    <div class="row">
                        <input type="text" name="username" placeholder="Username"> 
                    </div>
                    <div class="row">
                        <input type="password" name="password" placeholder="Password">
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Login" class="button"> 
                    </div>
                    <div class="row" style="text-align: center;">
                        Don't have an account? <br /><a href="createAccount.jsp">Sign up here!</a>
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
