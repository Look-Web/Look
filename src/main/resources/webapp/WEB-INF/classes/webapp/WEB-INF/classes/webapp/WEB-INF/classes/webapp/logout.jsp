<%-- 
    Document   : logout
    Created on : Apr 17, 2015, 4:46:32 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    if(session.getAttribute("user") != null) { 
        session.setAttribute("user", null);
    } 
    response.sendRedirect("index.jsp");
%>
