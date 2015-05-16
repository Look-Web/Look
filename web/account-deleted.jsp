<%-- 
    Document   : account-deleted
    Created on : Apr 19, 2015, 7:37:44 PM
    Author     : kevinholland
--%>

<%@page import="com.look.MenuBar"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="modal-dialogs.jsp"%>

<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect(".");
    } else {
        if (session.getAttribute("account_deleted") != null) {
            session.setAttribute("account_deleted", null);
        } else {
            response.sendRedirect(".");
        }
    }
%>

<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Account deleted</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    
    <%out.println(MenuBar.generateMenuBar(session, "Recent Feed"));%>
    
    <h3 align="center" style="margin-top: 15px">Your account has been deleted. We're sorry to see you go!</h3>
    
    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
</html>
