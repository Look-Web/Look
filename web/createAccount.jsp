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
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <title>Look! Registration</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>
        <h2>Create a Look! account</h2>
        <form action="createUser" method="post">
            <div class="row"">
                <div class="panel large-6 large-offset-3 medium-8 medium-offset-2 small-12 columns login-box">
                    <div class="row" style="text-align: center">
                        <h1>Register</h1>
                    </div>
                    <div class="row">
                        <label>
                        Username
                        <input type="text" name="username"> 
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        Password
                        <input type="password" name="password">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        Repeat Password
                        <input type="password" name="repeatPassword">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        First Name
                        <input type="text" name="firstName">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        Last Name
                        <input type="text" name="lastName">
                        </label>
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Register" class="button"> 
                    </div>
                </div>
            </div>
        </form>
        
        <%
            if (request.getAttribute("message") != null) {
                out.print(request.getAttribute("message"));
            }
        %>
    </body>
</html>
