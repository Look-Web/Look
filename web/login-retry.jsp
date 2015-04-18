<%-- 
    Document   : login-retry
    Created on : Apr 17, 2015, 4:29:06 PM
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
        <h1>Try again</h1>
        <h3>Please try again</h3>
        <p>The username or password was incorrect.<p>
        
        <form action="authorizeLogin" method="post">
            Username:<input type="text" name="username"> 
            Password:<input type="password" name="password">
            <input type="submit" value="Login"> 
        </form>
        <br/>
        Don't have an account? <a href="createAccount.jsp">Create one here!</a>
    </body>
</html>
