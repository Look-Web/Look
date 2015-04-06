<%-- 
    Document   : index
    Created on : Mar 19, 2015, 11:43:40 AM
    Author     : kevinholland
--%>

<%@page import="com.look.RecentFeed"%>
<!DOCTYPE html>
<html>
<head>
<style>
ul.menu {
    list-style-type: none;
    text-align: center;
    margin: 0;
    padding: 0;
    overflow: hidden;
}

li.menu {
    float: left;
}

div.feed {
    text-align: center;
}

a:link, a:visited {
    font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif;
    display: block;
    width: 120px;
    font-weight: bold;
    color: #FFFFFF;
    background-color: #44c2ff;
    text-align: center;
    padding: 4px;
    text-decoration: none;
    text-transform: uppercase;
}

a:hover, a:active {
    background-color: #1784ff;
}
.center {
    margin-left: auto;
    margin-right: auto;
    width: 70%;
}
</style>
</head>
    
    <div class="center">
        <ul class="menu">
          <li class="menu"><a href="#home" >Home</a></li>
          <li class="menu"><a href="#news">News</a></li>
          <li class="menu"><a href="#contact">Contact</a></li>
          <li class="menu"><a href="#about">About</a></li>
        </ul>
    </div>
    <body bgcolor=#626d72>
        <div class="feed center">
        <h1>Look Recent Feed</h1>
        <%  RecentFeed rf = new RecentFeed();
            out.println(rf.outputRecentFeed());
        %>
        </div>
    </body>
</html>
