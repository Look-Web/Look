<%-- 
    Document   : login
    Created on : Apr 17, 2015, 3:38:54 PM
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Login</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>
        
        <div class="contain-to-grid">
            <nav class="top-bar" data-topbar data-options="is_hover: false" role="navigation">
                <ul class="title-area">
                    <li class="name">
                        <h1><a href=".">Look!</a></h1>
                    </li>
                    <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
                </ul>

                <section class="top-bar-section">
                    <ul class="right">
                        <li><a href=".">Recent Feed</a></li>
                        <li><a href="upload.jsp">Upload an Image</a></li>
                        <%
                            if (session.getAttribute("user") != null) {
                                out.print("<li class='has-dropdown'><a href='#'>Hello, ");
                                out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                                out.print("!</a><ul class='dropdown'><li><a href='profile.jsp'>Profile</a></li>");
                                out.print("<li><a href='account.jsp'>Account Settings</a></li>");
                                out.print("<li><a href='logout.jsp'>Logout</a></li></ul></li>");
                            } else {
                                out.print("<li><a href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>");
                            }
                        %>
                    </ul>
                </section>
            </nav>
        </div>
        
        <form action="authorizeLogin" method="post">
            <div class="row">
                <div class="panel large-4 large-offset-4 medium-6 medium-offset-3 small-12 columns login-box">
                    <div class="row" style="text-align: center">
                        <h4>Already have an account? Log in here.</h4>
                    </div>
                    <div class="row">
                        <input type="text" name="username" placeholder="Username"> 
                    </div>
                    <div class="row">
                        <input type="password" name="password" placeholder="Password">
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Login" class="button"> 
                    </div>
                    <div class="row" style="text-align: center;">
                        Don't have an account? <br /><a href="createAccount.jsp">Sign up here!</a>
                    </div>
                </div>
            </div>
        </form>
    
    <script src="js/vendor/jquery.js"></script>
        <script src="js/foundation.min.js"></script>
        <script>
            $(document).foundation();
        </script>
    </body>
</html>
