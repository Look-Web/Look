<%-- 
    Document   : profile
    Created on : Apr 19, 2015, 6:24:18 PM
    Author     : kevinholland
--%>

<%@page import="com.look.DatabaseUserUtils"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%  //if not logged in
    if(session.getAttribute("user") == null) { 
        session.setAttribute("destination", "profile.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Account</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    <body>
        
        <div class="contain-to-grid">
            <nav class="top-bar" data-topbar data-options="is_hover: false" role="navigation">
                <ul class="title-area">
                    <li class="name">
                        <h1><a href="#">Look!</a></h1>
                    </li>
                    <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
                </ul>

                <section class="top-bar-section">
                    <ul class="right">
                        <li><a href=".">Recent Feed</a></li>
                        <li><a href="upload.jsp">Upload an Image</a></li>
                        <%
                            if (session.getAttribute("user") != null) {
                                out.print("<li class='has-dropdown active'><a href='#'>Hello, ");
                                out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                                out.print("!</a><ul class='dropdown'><li class='active'><a href='profile.jsp'>Profile</a></li>");
                                out.print("<li class='active'><a href='logout.jsp'>Logout</a></li></ul></li>");
                            } else {
                                out.print("<li><a href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>");
                            }
                        %>
                    </ul>
                </section>
            </nav>
        </div>
    
        <div class="row">
            <h2>Account</h2>
            <h4>Settings for your Look! account</h4>
            <form method="POST" action="changeAccount">
                <table>
                    <tr>
                        <td>Username</td>
                        <td>${sessionScope.user}</td>
                    </tr>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" 
                                   value="<%out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));%>"/></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lastName" 
                                   value="<%out.print(DatabaseUserUtils.getLastNameFromUsername(session.getAttribute("user").toString()));%>"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Save Changes" class="button"/></td><td/>
                    </tr>
                </table>
            </form>
            <br/>
            <a href="deleteAccount" style="color: red;" >Delete account</a>
            <!-- TODO MODAL ARE YOU SURE??? VERY DANGEROUS WITHOUT IT-->
        </div>
        
    <script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
    </body>
</html>
