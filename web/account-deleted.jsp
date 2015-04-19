<%-- 
    Document   : account-deleted
    Created on : Apr 19, 2015, 7:37:44 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | Account Deleted</title>
    </head>
    <body>
        <h3>Your account has been deleted, we're sorry to see you go!</h3>
    </body>
</html>
