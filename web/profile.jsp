<%-- 
    Document   : profile
    Created on : Apr 19, 2015, 6:24:18 PM
    Author     : kevinholland
--%>

<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  //if not logged in
    if(session.getAttribute("user") == null) { 
        session.setAttribute("destination", "profile.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | Account</title>
    </head>
    <body>
        <h2>Account</h2>
        <h4>Settings for your Look! account</h4>
        <form method="POST" action="changeAccount">
            <table>
                <tr>
                    <td>Username</td>
                    <td>${sessionScope.user}</td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" 
                               value="<%out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));%>"/></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lastName" 
                               value="<%out.print(DatabaseUserUtils.getLastNameFromUsername(session.getAttribute("user").toString()));%>"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Save Changes"/></td>
                </tr>
            </table>
        </form>
        <a href="deleteAccount" style="color: red;">Delete account</a>
    </body>
</html>
