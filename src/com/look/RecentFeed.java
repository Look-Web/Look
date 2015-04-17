package com.look;

import static java.lang.System.out;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinholland
 */
public class RecentFeed {
    java.sql.Connection con;
    private static final String db = "look_db";
    private static final String db_user = "look_admin";
    private static final String db_password = "lookpass";
    
    public RecentFeed() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/"+db, db_user, db_password);
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
    
    public String outputRecentFeed() throws SQLException {
        Statement s;
        try {
            s = con.createStatement();
        } catch (NullPointerException e) {
            return e.getMessage();
        }
        ResultSet r;
        String output = "";
        try {
            r = s.executeQuery("SELECT * FROM posts ORDER BY time_posted DESC");
            //out.println("<pre>");
            while (r.next()) {
                Statement sUser = con.createStatement();
                int userID = r.getInt(4);
                String query = "SELECT username FROM users WHERE user_id=" + userID;
                ResultSet userResult = sUser.executeQuery(query);
                userResult.next();
                String user = userResult.getString("username");
                output += "<div class='large-3 medium-4 small-12 columns'>";
                output += "<div class='panel feed-image'>";
                output += String.format("<img src='images/%s' />", r.getString(5));
                output += String.format("<span class='feed-image-title'>%s</span><br />", r.getString(2));
                output += String.format("<span class='feed-image-attribution'>posted by %s</span><br />", user);
                output += String.format("<span class='feed-image-timestamp'>%s</span>", r.getTimestamp(6).toString());
                output += "</div>";
                output += "</div>";
            }
        } catch (SQLException e) {
            out.println(e);
        }
        return output;
    }
}