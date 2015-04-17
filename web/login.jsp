<%-- 
    Document   : login
    Created on : Apr 17, 2015, 3:38:54 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% if(session.getAttribute("user") != null) { 
        response.sendRedirect("index.jsp");
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="authorizeLogin" method="post">
            Username:<input type="text" name="username"> 
            Password:<input type="password" name="password">
            <input type="submit" value="Login"> 
        </form>
    </body>
</html>
