<%-- 
    Document   : index
    Created on : Mar 27, 2015, 7:51:58 PM
    Author     : tmcdeane
--%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.RecentFeed" %>
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

    <!--MODAL BOXES-->
    <div id="loginModal" class="reveal-modal small login-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
        <form action="authorizeLogin" method="post">
            <div class="row">
                <div class="small-12 columns">
                    <div class="row" style="text-align: center">
                        <h4>Already have an account? Log in here.</h4>
                    </div>
                    <div class="row">
                        <input type="text" name="username" placeholder="Username"> 
                    </div>
                    <div class="row">
                        <input type="password" name="password" placeholder="Password">
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Login" class="button"> 
                    </div>
                    <div class="row" style="text-align: center;">
                        Don't have an account? <br /><a href="createAccount.jsp" data-reveal-id='registerModal'>Sign up here!</a>
                    </div>
                </div>
            </div>
        </form>
        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
    </div>

    <div id="registerModal" class="reveal-modal medium" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
        <form action="createUser" method="post">
            <div class="row">
                <div class="small-12 columns">
                    <div class="row" style="text-align: center">
                        <h2>Register</h2>
                    </div>
                    <div class="row">
                        <label>
                            <input type="text" name="username" placeholder="Username"> 
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="password" name="password" placeholder="Password">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="password" name="repeatPassword" placeholder="Repeat Password">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="text" name="firstName" placeholder="First Name">
                        </label>
                    </div>
                    <div class="row">
                        <label>
                            <input type="text" name="lastName" placeholder="Last Name">
                        </label>
                    </div>
                    <div class="row" style="text-align: center;">
                        <input type="submit" value="Register" class="button"> 
                    </div>
                </div>
            </div>
        </form>
        <a class="close-reveal-modal" aria-label="Close">&#215;</a>
    </div>


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
                    <li class="has-form">
                        <form method='GET' action='search'>
                        <div class="row">
                        
                            <input class="search-box" type='text' name='tag' placeholder='Search by tag here'>
                        </div>
                        </form>
                    </li>
                    <li class="active"><a href="#">Recent Feed</a></li>
                    <li><a href="upload.jsp">Upload an Image</a></li>
                        <%
                            if (session.getAttribute("user") != null) {
                                out.print("<li class='has-dropdown'><a href='#'>Hello, ");
                                out.print(DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString()));
                                out.print("!</a><ul class='dropdown'><li><a href='profile.jsp'>Profile</a></li>");
                                out.print("<li><a href='logout.jsp'>Logout</a></li></ul></li>");
                            } else {
                                out.print("<li><a href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>");
                            }
                        %>
                </ul>
            </section>
        </nav>
    </div>


    <div class="row" style="margin-top: 15px">
        <div class="small-12 columns"><h2 style="text-align: center">Recent Feed</h2></div>
    </div>                    

    <div class="row">
        <%  RecentFeed rf = new RecentFeed();
            out.println(rf.outputRecentFeed());
        %>
    </div>

    e<script src="js/vendor/jquery.js"></script>
    <script src="js/foundation.min.js"></script>
    <script>
        $(document).foundation();
    </script>
</html>