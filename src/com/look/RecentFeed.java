package com.look;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
 *
 * @author kevinholland
 */
public class RecentFeed {
    public static String outputRecentFeed(int fetchSize) throws SQLException, ClassNotFoundException {
        //establish db connection
        Connection con = LookDatabaseUtils.getNewConnection();
        //output HTML
        String outputHtml = "";
        //PrettyTime instance for time formatting
        PrettyTime p = new PrettyTime();
        try {
            //create statement for query
            Statement s = con.createStatement();
            //limit number of query results
            s.setFetchSize(fetchSize);
            //query all posts ordered by descending time (most recent first)
            ResultSet r = s.executeQuery("SELECT * FROM posts ORDER BY time_posted DESC");
            
            //iterate through result set
            while (r.next()) {
                //GET USERNAME FOR IMAGE POST
                Statement sUser = con.createStatement();
                int userID = r.getInt(4);
                String query = "SELECT username FROM users WHERE user_id=" + userID;
                ResultSet userResult = sUser.executeQuery(query);
                userResult.next();
                String user = userResult.getString("username");
                
                //get timestamp and instantiate date object from it
                Timestamp stamp = r.getTimestamp(6);
                Date date = new Date(stamp.getTime());
                
                //get post ID from post query result set
                int post_id = r.getInt("post_id");
                
                //construct HTML for post preview on the recent feed
                outputHtml += String.format("<a href='%s'>", "./post?id=" + post_id); // TODO: Replace this with the image link
                outputHtml += "<div class='large-3 medium-4 small-12 columns'>";
                outputHtml += "<div class='panel feed-image'>";
                outputHtml += String.format("<div class='feed-image-src' style='background-image: url(images/%s)'></div>", r.getString(5));
                outputHtml += String.format("<span class='feed-image-title'>%s</span><br />", r.getString(2));
                outputHtml += String.format("<span class='feed-image-attribution'>posted by %s</span><br />", user);
                outputHtml += String.format("<span class='feed-image-timestamp'>%s</span>", p.format(date));
                outputHtml += "</div>";
                outputHtml += "</div>";
                outputHtml += "</a>";
            }
        } catch (SQLException e) {
            out.println(e);
        }
        return outputHtml;
    }
}