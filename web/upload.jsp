<%-- 
    Document   : upload
    Created on : Apr 17, 2015, 4:36:02 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%  //if not logged in
    if(session.getAttribute("user") == null) { 
        session.setAttribute("destination", "/upload.jsp");
        request.getRequestDispatcher("login.jsp").forward(request, response);
}
%>
<html>
<head>
<title>Upload Image</title>
</head>
<body>
    <a href="index.jsp">Look! Home</a>
    <center>
        <h1>Upload Image</h1>
        <form method="POST" action="uploadServlet" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>Title: </td>
                    <td><input type="text" name="title" size="50"/></td>
                </tr>
                <tr>
                    <td>Description: </td>
                    <td><input type="text" name="description" size="50"/></td>
                </tr>
                <tr>
                    <td>Tags: (Separated by spaces)</td>
                    <td><input type="text" name="tags" size="50"/></td>
                </tr>
                <tr>
                    <td>Image: </td>
                    <td><input type="file" name="image" size="50"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Upload">
                    </td>
                </tr>
            </table>
        </form>
    </center>
</body>
</html>
