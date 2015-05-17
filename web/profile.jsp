<%-- 
    Document   : profile
    Created on : Apr 20, 2015, 12:21:29 AM
    Author     : kevinholland
--%>

<%@page import="com.look.MenuBar.Items"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.PostResultDisplay"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.look.MenuBar"%>
<%@include file="modal-dialogs.jsp"%>

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
        
        <%out.println(MenuBar.generateMenuBar(session, Items.USER_PROFILE));%>

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
