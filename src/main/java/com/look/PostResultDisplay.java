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

/*
 * Copyright 2015 Kevin Holland.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * This class contains a static method to generate HTML for displaying multiple
 * posts in a grid layout
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
public class PostResultDisplay {

    public static Connection conn;

    /**
     * Generates HTML of multiple posts in a grid layout
     * @param postIDs Post IDs to be displayed in order. Must be IDs separated by spaces
     * @return String of HTML
     */
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

    /**
     * Generates HTML for a specific post given a ResultSet. 
     * Only uses the current Result. Does not iterate. 
     * @param post ResultSet with post data to display
     * @return String of HTML for post
     * @throws SQLException 
     */
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
