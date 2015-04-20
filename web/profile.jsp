<%-- 
    Document   : profile
    Created on : Apr 20, 2015, 12:21:29 AM
    Author     : kevinholland
--%>

<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.PostResultDisplay"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        session.setAttribute("destination", "myProfile");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    if (request.getAttribute("username") == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | ${requestScope.username} Profile </title>
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
                                out.print("<li class='has-dropdown active'><a href='#'>Hello, ");
                                out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                                out.print("!</a><ul class='dropdown active'><li class='active'><a href='myProfile'>Profile</a></li>");
                                out.print("<li class='active'><a href='account.jsp'>Account Settings</a></li>");
                                out.print("<li class='active'><a href='logout.jsp'>Logout</a></li></ul></li>");
                            } else {
                                out.print("<li><a href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>");
                            }
                        %>
                    </ul>
                </section>
            </nav>
        </div>

        <h3 class='row' style="margin-top: 15px">
            <%
                //username and date joined
                if (request.getAttribute("username") != null) {
                    out.print(request.getAttribute("username").toString());
                }
            %>
        </h3>
        <h4 class='row' style="margin-top: 15px">
            Joined 
            <%
                if (request.getAttribute("timeJoined") != null) {
                    out.print(request.getAttribute("timeJoined").toString());
                }
            %>
        </h4>
        <h4 class='row' style="margin-top: 15px">
            <%
                if (request.getAttribute("numPosts") != null) {
                    int numPosts = Integer.parseInt(request.getAttribute("numPosts").toString());

                    if (numPosts == 0) {
                        out.print("This user has no posts yet");
                    } else if (numPosts == 1) {
                        out.print("1 post");
                    } else {
                        out.print(numPosts + " posts");
                    }
                }
            %>
        </h4>

        <div class="row">
            <%
                if (request.getAttribute("postIDs") != null) {
                    out.print(PostResultDisplay.displayPostsFromIDs(request.getAttribute("postIDs").toString()));
                }
            %>
        </div>

        <script src="js/vendor/jquery.js"></script>
        <script src="js/foundation.min.js"></script>
        <script>
            $(document).foundation();
        </script>
    </body>
</html>
