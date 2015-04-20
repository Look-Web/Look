<%-- 
    Document   : upload
    Created on : Apr 17, 2015, 4:36:02 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.RecentFeed" %>
<!DOCTYPE html>
<%  //if not logged in
    if(session.getAttribute("user") == null) { 
        session.setAttribute("destination", "upload.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }   
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! Upload</title>
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
                        <li><a href="index.jsp">Recent Feed</a></li>
                        <li class="active"><a href="upload.jsp">Upload an Image</a></li>
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
        
        
        <form action="uploadServlet" enctype="multipart/form-data" method="post">
            <div class="row">
                <div class="panel large-6 large-offset-3 medium-8 medium-offset-2 small-12 columns login-box">
                    <div class="row" style="text-align: center">
                        <h3>Image Upload</h3>
                    </div>
                    <div class="row">
                        <label>
                            Title
                            <input type="text" placeholder="Image Title" name="title" value="${requestScope.title}"> 
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        Description
                        <textarea name="description" placeholder="enter a brief description of the image.">${requestScope.description}</textarea>
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        Tags
                        <input type="text" placeholder="enter #hashtags separated by spaces" name="tags"  value="${requestScope.tags}"/>
                        </label>
                    </div>
                    <div class="row">
                        <label>
                        Image
                        <input type="file" name="image" accept="image/*"/>
                        </label>
                    </div>
                    <div class="row" style="text-align: center;">
                        <p style="color: red;">
                            <%
                                if (request.getAttribute("message") != null) {
                                    out.print(request.getAttribute("message"));
                                }
                            %>
                        </p>
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Upload" class="button"> 
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
