<%-- 
    Document   : upload
    Created on : Apr 13, 2015, 10:18:16 PM
    Author     : kevinholland
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload Image</title>
</head>
<body>
    <a href="index.jsp">Look! Home</a>
    <center>
        <h1>Upload Image</h1>
        <form method="POST" action="uploadServlet" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>User: (This will be changed obviously)</td>
                    <td><input type="text" name="user" size="50"/></td>
                </tr>
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
