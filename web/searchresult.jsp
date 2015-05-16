<%-- 
    Document   : search-result
    Created on : Apr 19, 2015, 4:56:30 PM
    Author     : kevinholland
--%>

<%@page import="com.look.MenuBar"%>
<%@page import="com.look.DatabaseUserUtils"%>
<%@page import="com.look.PostResultDisplay"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="modal-dialogs.jsp"%>


<%
    if (request.getAttribute("searchTag") == null) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Look! | Search ${requestScope.searchTag}</title>
        <link rel="stylesheet" href="css/foundation.css" />
        <link rel="stylesheet" href="css/styles.css" />
        <script src="js/vendor/modernizr.js"></script>
    </head>
    
    <%out.println(MenuBar.generateMenuBar(session, "Recent Feed"));%>

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
