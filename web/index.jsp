<%-- 
    Document   : index
    Created on : Mar 27, 2015, 7:51:58 PM
    Author     : tmcdeane
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.RecentFeed" %>
<%@page import="com.look.MenuBar"%>
<%@include file="modal-dialogs.jsp"%>

<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Welcome</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>

    <%out.println(MenuBar.generateMenuBar(session, "Recent Feed"));%>

    <div class="row" style="margin-top: 15px">
        <div class="small-12 columns"><h2 style="text-align: center">Recent Feed</h2></div>
    </div>                    

    <div class="row">
        <%  try {
                out.println(RecentFeed.outputRecentFeed(64));
            } catch (Exception e) {
                out.println("<h4>We're sorry, there was an error. Please try again or refresh the page</h4>");
            }
        %>
    </div>

    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
</html>