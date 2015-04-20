<%-- 
    Document   : search-result
    Created on : Apr 19, 2015, 4:56:30 PM
    Author     : kevinholland
--%>

<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.PostResultDisplay"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    if (request.getAttribute("searchTag") == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | Search ${requestScope.searchTag}</title>
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

    <div class="row" style="margin-top: 15px">
        <h3>
            <%
                //find num of results
                if (request.getAttribute("postIDs") != null && !request.getAttribute("postIDs").equals("")) {
                    List<String> results = Arrays.asList(request.getAttribute("postIDs").toString().split(" "));
                    if (results.size() > 1) {
                        out.print("Found " + results.size() + " results for " + request.getAttribute("searchTag"));
                    } else if (results.size() == 1) {
                        out.print("Found 1 result for " + request.getAttribute("searchTag"));
                    }
                } else {
                    out.print("<div align='center'>");
                    out.print("No results found for " + request.getAttribute("searchTag") + ". ");
                    out.print("Look for something different!");
                    out.print("</div>");
                }
            %>
        </h3>
    </div>
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
