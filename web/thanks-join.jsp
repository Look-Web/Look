<%-- 
    Document   : account-deleted
    Created on : Apr 19, 2015, 7:37:44 PM
    Author     : kevinholland
--%>

<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect(".");
    } else {
        if (session.getAttribute("account_created") != null) {
            session.setAttribute("account_created", null);
        } else {
            response.sendRedirect(".");
        }
    }
%>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Joined!</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    
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
                        <li class="has-form">
                            <form method='GET' action='search'>
                                <div class="row">

                                    <input class="search-box" type='text' name='tag' placeholder='Search by tag here'>
                                </div>
                            </form>
                        </li>
                        <li><a href=".">Recent Feed</a></li>
                        <li><a href="upload.jsp">Upload an Image</a></li>
                            <%
                                if (session.getAttribute("user") != null) {
                                    out.print("<li class='has-dropdown'><a href='#'>Hello, ");
                                    out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                                    out.print("!</a><ul class='dropdown'><li><a href='myProfile'>Profile</a></li>");
                                    out.print("<li><a href='account.jsp'>Account Settings</a></li>");
                                    out.print("<li><a href='logout.jsp'>Logout</a></li></ul></li>");
                                } else {
                                    out.print("<li class='active'><a href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>");
                                }
                            %>
                    </ul>
                </section>
            </nav>
        </div>
    
    <h3 align="center" style="margin-top: 15px">Thanks for joining! Start by looking at the Recent Feed!</h3>
    
    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
</html>
