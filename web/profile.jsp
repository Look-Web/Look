<%-- 
    Document   : profile
    Created on : Apr 20, 2015, 12:21:29 AM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(".");
    }
    if (request.getAttribute("username") == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | ${requestScope.username}</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
