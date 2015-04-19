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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinholland
 */
@WebServlet("/post")
public class PostPageServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
            String post_id = request.getParameter("id");
            ResultSet r = conn.createStatement().executeQuery(
                "SELECT * FROM posts "
                + "WHERE post_id=" + post_id + ";");
            r.next();
            request.setAttribute("title", r.getString("title"));
            request.setAttribute("description", r.getString("description"));
            
            ResultSet userSet = conn.createStatement().executeQuery(
                "SELECT username FROM users "
                + "WHERE user_id=" + r.getInt("users_user_ID") + ";");
            userSet.next();
            
            request.setAttribute("username", userSet.getString(1));
            
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
            Timestamp stamp = r.getTimestamp("date_posted");
            Date date = new Date(stamp.getTime());
            request.setAttribute("time", p.format(date));
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostPageServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }
}
