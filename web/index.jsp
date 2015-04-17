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
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    

        <div class="contain-to-grid">
            <nav class="top-bar" data-topbar role="navigation">
                <ul class="title-area">
                    <li class="name">
                        <h1><a href="#">Look!</a></h1>
                    </li>
                    <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
                </ul>

                <section class="top-bar-section">
                    <ul class="right">
                        <li class="active"><a href="#">Recent Feed</a></li>
                        <li><a href="upload.html">Upload an Image</a></li>
                        <li><a href="#">Sign Up/Login</a></li>
                    </ul>
                </section>
            </nav>
        </div>

        <div class="row" style="margin-top: 50px">
            <div class="small-12 columns"><h1 style="text-align: center">Recent Feed</h1></div>            
            
            <%  RecentFeed rf = new RecentFeed();
            out.println(rf.outputRecentFeed());
            %>
        </div>

        <script src="js/vendor/jquery.js"></script>
        <script src="js/foundation.min.js"></script>
        <script>
            $(document).foundation();
        </script>
    
</html>