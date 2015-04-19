<%-- 
    Document   : login-retry
    Created on : Apr 17, 2015, 4:29:06 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.RecentFeed" %>
<!DOCTYPE html>
<% if(session.getAttribute("user") != null) { 
        response.sendRedirect("index.jsp");
}
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! Login</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
    </head>
    <body>
        <div class="contain-to-grid">
            <nav class="top-bar" data-topbar role="navigation">
                <ul class="title-area">
                    <li class="name">
                        <h1><a href="index.jsp">Look!</a></h1>
                    </li>
                    <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
                </ul>

                <section class="top-bar-section">
                    <ul class="right">
                        <%
                            if (session.getAttribute("user") != null) {
                                out.print("<li><a>Hello, ");
                                out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                                out.print("!</a></li>");
                            }
                        %>
                        <li><a href="index.jsp">Recent Feed</a></li>
                        <li><a href="upload.jsp">Upload an Image</a></li>
                        <%
                            if (session.getAttribute("user") != null) {
                                out.print("<li><a href='logout.jsp'>Logout</a></li>");
                            } else {
                                out.print("<li class='active'><a href='login.jsp'>Sign Up/Login</a></li>");
                            }
                        %>
                    </ul>
                </section>
            </nav>
        </div>
        
        <form action="authorizeLogin" method="post">
            <div class="row"">
                <div class="panel large-4 large-offset-4 medium-6 medium-offset-3 small-12 columns login-box">
                    <div class="row" style="text-align: center">
                        <h1>Login</h1>
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
                    <div class="row" style="text-align: center;">
                        <p style="color: red;">You entered an incorrect username or password. Please try again.</p>
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Login" class="button"> 
                    </div>
                    <div class="row" style="text-align: center;">
                        Don't have an account? <br /><a href="createAccount.jsp">Create one here!</a>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
