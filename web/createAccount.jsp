<%-- 
    Document   : createAccount
    Created on : Apr 17, 2015, 5:19:08 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Look! Registration</title>
    </head>
    <body>
        <h2>Create a Look! account</h2>
        <form action="createUser" method="POST">
            <table>
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" value='${requestScope.username}'/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password"/></td>
                </tr>
                <tr>
                    <td>Repeat Password</td>
                    <td><input type="password" name="repeatPassword"/></td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" value='${requestScope.firstName}'/></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lastName" value='${requestScope.lastName}'/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Start Looking!"/></td>
                </tr>
            </table>
        </form>
        <%
            if (request.getAttribute("message") != null) {
                out.print(request.getAttribute("message"));
            }
        %>
    </body>
</html>
