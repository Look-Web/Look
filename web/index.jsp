<%-- 
    Document   : index
    Created on : Mar 19, 2015, 11:43:40 AM
    Author     : kevinholland
--%>

<%@ page import = "java.sql.*"%>
<html>
    <body>
        <h1>Look Recent Feed (test)</h1>
        <%
            String db = "look_db";
            String db_user = "kholland950";
            String db_pw = "m47dyrpC5HfRdMEb";

            try {
                java.sql.Connection con;
                Class.forName("org.gjt.mm.mysql.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/"+db, db_user, db_pw);

                Statement s = con.createStatement();
                ResultSet r = s.executeQuery("SELECT * FROM posts ORDER BY time_posted DESC");
                //out.println("<pre>");
                while (r.next()) {
                    Statement sUser = con.createStatement();
                    int userID = r.getInt(4);
                    String query = "SELECT username FROM users WHERE user_id=" + userID;
                    ResultSet userResult = sUser.executeQuery(query);
                    userResult.next();
                    String user = userResult.getString("username");
                    out.println("<hr>");
                    out.println("<p>");
                    out.println(String.format("<h2>%s</h2>", r.getString(2)));
                    out.println(String.format("<h3>Post by: %s</h3>", user));
                    out.println(String.format("<h4>%s</h4>", r.getString(3)));
                    out.println(String.format("<p>%s</p>", r.getTimestamp(6).toString()));
                    out.println(String.format("<img src=%s>", r.getString(5)));
                    out.println("</p>");
                    out.println("<hr>");
                }
            } catch (SQLException e) {
                out.println("SQLException caught: " + e.getMessage());
            }
        %>
    </body>
</html>

