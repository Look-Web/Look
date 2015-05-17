<%-- 
    Document   : post
    Created on : Apr 19, 2015, 3:03:31 PM
    Author     : kevinholland
--%>

<%@page import="com.look.MenuBar.Items"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.look.MenuBar"%>
<%@include file="modal-dialogs.jsp"%>

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
    
    <%out.println(MenuBar.generateMenuBar(session, Items.RECENT_FEED));%>

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
