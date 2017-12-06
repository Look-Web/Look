<%-- 
    Document   : profile
    Created on : Apr 19, 2015, 6:24:18 PM
    Author     : kevinholland
--%>

<%@page import="com.look.MenuBar.Items"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.look.MenuBar"%>
<%@include file="modal-dialogs.jsp"%>

<%  //if not logged in
    if(session.getAttribute("user") == null) { 
        session.setAttribute("destination", "account.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Account</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>
        
        <%out.println(MenuBar.generateMenuBar(session, Items.USER_ACCOUNT));%>
        
        <div align="center">
            <div class="row">
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
                        <p style="color: red;">
                            <%
                                if (request.getAttribute("message") != null) {
                                    out.print(request.getAttribute("message"));
                                }
                            %>
                        </p>
                        <tr>
                            <td><input type="submit" value="Save Changes" class="button"/></td><td/>
                        </tr>
                    </table>
                </form>
                <br/>
                <a href="deleteAccount" style="color: red;" >Delete account</a>
                <!-- TODO MODAL ARE YOU SURE??? VERY DANGEROUS WITHOUT IT-->
            </div>
        </div>
        
    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
    </body>
</html>
