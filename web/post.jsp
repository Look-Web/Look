<%-- 
    Document   : post
    Created on : Apr 19, 2015, 3:03:31 PM
    Author     : kevinholland
--%>

<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | ${requestScope.title}</title>
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
                    <li class="active"><a href=".">Recent Feed</a></li>
                    <li><a href="upload.jsp">Upload an Image</a></li>

                    <%
                        if (session.getAttribute("user") != null) {
                            out.print("<li class='has-dropdown'><a href='#'>Hello, ");
                            out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                            out.print("!</a><ul class='dropdown'><li><a href='myProfile'>Profile</a></li>");
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

    <div class='row'>
    <h2>${requestScope.title}</h2>
    <%
        if (request.getAttribute("username") != null && session.getAttribute("user") != null) {
            //if username of post matches user of session
            if (request.getAttribute("username").toString().equals(session.getAttribute("user").toString())) {
                session.setAttribute("deleting", "yes"); //stops url gets from deleting posts
                out.print("<a href='deletePost?id=" + request.getAttribute("postID") + "' >Delete post</a>");
            }
        }
    %>
    <img src='images/${requestScope.image}'/>
    <h4>${requestScope.time} by ${requestScope.username}</h4>
    <h4>${requestScope.description}</h4>
    <h4>${requestScope.tags}</h4>
    </div>

    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
</html>
