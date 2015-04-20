/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ocpsoft.prettytime.PrettyTime;

/**
 *
 * @author kevinholland
 */
public class PostResultDisplay {

    public static Connection conn;

    public static String displayPostsFromIDs(String postIDs) {
        if (postIDs.equals("")) {
            //no results were found
            return "";
        }
        String output = "";
        List<String> postIDList = Arrays.asList(postIDs.split(" "));
        //TODO any initialization to the output

        //results were found, display posts
        conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();

            for (String postID : postIDList) {
                int post_id = Integer.parseInt(postID);
                ResultSet post = conn.createStatement().executeQuery(
                        "SELECT * FROM posts WHERE post_id=" + post_id + ";");
                post.first();
                output += displayPost(post);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PostResultDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

    private static String displayPost(ResultSet post) throws SQLException {
        Statement sUser = conn.createStatement();
        int userID = post.getInt(4);
        String query = "SELECT username FROM users WHERE user_id=" + userID;
        ResultSet userResult = sUser.executeQuery(query);
        userResult.next();

        PrettyTime p = new PrettyTime();
        String output = "";
        int post_id = post.getInt("post_id");
        String user = userResult.getString("username");
        Timestamp stamp = post.getTimestamp(6);
        Date date = new Date(stamp.getTime());
        output += String.format("<a href='%s'>", "./post?id=" + post_id); // TODO: Replace this with the image link
        output += "<div class='large-3 medium-4 small-12 columns'>";
        output += "<div class='panel feed-image'>";
        output += String.format("<div class='feed-image-src' style='background-image: url(images/%s)'></div>", post.getString(5));
        output += String.format("<span class='feed-image-title'>%s</span><br />", post.getString(2));
        output += String.format("<span class='feed-image-attribution'>posted by %s</span><br />", user);
        output += String.format("<span class='feed-image-timestamp'>%s</span>", p.format(date));
        output += "</div>";
        output += "</div>";
        output += "</a>";
        return output;
    }
}
