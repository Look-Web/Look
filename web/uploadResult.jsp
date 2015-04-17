<%-- 
    Document   : uploadResult
    Created on : Apr 16, 2015, 5:54:18 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% if(session.getAttribute("user") == null) { 
        response.sendRedirect("login.jsp");
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Result</title>
    </head>
    <body>
         <p>Message: ${message}</p>
    </body>
</html>
