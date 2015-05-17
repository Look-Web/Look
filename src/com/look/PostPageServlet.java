package com.look;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * PostPageServlet processes a "/post" get request and forwards to the image post page
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
@WebServlet("/post")
public class PostPageServlet extends HttpServlet {
    /**
     * Get request for image post page
     * @param request HttpServletRequest from client
     * @param response HttpServletResponse to send to client
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn;
        int post_id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("postID", post_id);
            
        try {
            conn = LookDatabaseUtils.getNewConnection();
            
            ResultSet r = conn.createStatement().executeQuery(
                "SELECT * FROM posts "
                + "WHERE post_id=" + post_id + ";");
            r.next();
            request.setAttribute("title", r.getString("title"));
            request.setAttribute("description", r.getString("description"));
            int user_id = r.getInt("users_user_ID");
            ResultSet userSet = conn.createStatement().executeQuery(
                "SELECT username FROM users "
                + "WHERE user_id=" + user_id + ";");
            userSet.next();
            
            request.setAttribute("username", userSet.getString(1));
            request.setAttribute("userID", user_id);
            ResultSet tagIDSet = conn.createStatement().executeQuery(
                    "SELECT * FROM tags_has_posts "
                    + "WHERE posts_post_id=" + post_id + ";");
            //tagIDSet is now a set of all tags of post
            //iterate through these
            String tags = "";
            while (tagIDSet.next()) {
                int tag_id = tagIDSet.getInt(1);
                ResultSet tagSet = conn.createStatement().executeQuery(
                        "SELECT tag FROM tags WHERE tag_id=" + tag_id + ";");
                tagSet.next();
                String tag = tagSet.getString(1);
                tags += "#" + tag + " ";
            }
            
            request.setAttribute("tags", tags);
            
            request.setAttribute("image", r.getString("image_url"));
            
            //set time to pretty time
            PrettyTime p = new PrettyTime();
            Timestamp stamp = r.getTimestamp("time_posted");
            Date date = new Date(stamp.getTime());
            request.setAttribute("time", p.format(date));
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PostPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }
}
