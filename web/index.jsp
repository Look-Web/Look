<%-- 
    Document   : index
    Created on : Mar 27, 2015, 7:51:58 PM
    Author     : tmcdeane
--%>
<%@page import="com.look.RecentFeed" %>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Foundation | Welcome</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>

        <div class="row">
            <div class="large-12 columns">
                <h1>Welcome to Foundation</h1>
            </div>
        </div>

        <div class="row">
            <%  RecentFeed rf = new RecentFeed();
            out.println(rf.outputRecentFeed());
            %>
        </div>

        <script src="js/vendor/jquery.js"></script>
        <script src="js/foundation.min.js"></script>
        <script>
            $(document).foundation();
        </script>
    </body>
</html>