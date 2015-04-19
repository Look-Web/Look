<%-- 
    Document   : search-result
    Created on : Apr 19, 2015, 4:56:30 PM
    Author     : kevinholland
--%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="com.look.SearchResultDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Look! | Search ${requestScope.tag}</title>
    </head>
    <body>
        <h3>
            <%
                //find num of results
                if (!request.getAttribute("postIDs").equals("")) {
                    List<String> results = Arrays.asList(request.getAttribute("postIDs").toString().split(" "));
                    if (results.size() > 0) {
                        out.print("Found " + results.size() + " results for " + request.getAttribute("searchTag"));
                    }
                } else {
                    out.print("No results found for " + request.getAttribute("searchTag"));
                }
            %>
        </h3>
        <%
            out.print(SearchResultDisplay.displaySearch(request.getAttribute("postIDs").toString()));
        %>
    </body>
</html>
