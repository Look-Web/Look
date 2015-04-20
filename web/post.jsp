<%-- 
    Document   : post
    Created on : Apr 19, 2015, 3:03:31 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | ${requestScope.title}</title>
    </head>
    <body>
        <h2>${requestScope.title}</h2>
        <h2>${requestScope.username}</h2>
        <h2>${requestScope.description}</h2>
        <h2>${requestScope.tags}</h2>
        <h3>${requestScope.time}</h3>
        <img src='images/${requestScope.image}'/>
    </body>
</html>
