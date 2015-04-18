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
<title>Look! - Upload Image</title>
</head>
<body>
    
    <a href="#" data-reveal-id="myModal">Click Me For A Modal</a>

<div id="myModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
  <h2 id="modalTitle">Awesome. I have it.</h2>
  <p class="lead">Your couch.  It is mine.</p>
  <p>I'm a cool paragraph that lives inside of an even cooler modal. Wins!</p>
  <a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>
    
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
                    <td>Tags: (Separated by commas)</td>
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
