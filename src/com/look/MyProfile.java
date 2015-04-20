/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ocpsoft.prettytime.PrettyTime;

/**
 *
 * @author kevinholland
 */
@WebServlet("/myProfile")
public class MyProfile extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("destination", "myProfile");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        String username = request.getSession().getAttribute("user").toString();
        request.setAttribute("username", username);
        int user_id = DatabaseUserUtils.getUserIDFromUsername(username);
        Connection conn = null;
        String postIDsString = "";
        int numOfPosts = 0;
        try {
            conn = LookDatabaseUtils.getNewConnection();
            ResultSet postsSet = conn.createStatement().executeQuery(
                    "SELECT post_id FROM posts "
                    + "WHERE users_user_id=" + user_id + ";");
            while (postsSet.next()) {
                postIDsString += postsSet.getInt(1) + " ";
                numOfPosts++;
            }
            
            request.setAttribute("postIDs", postIDsString);
            request.setAttribute("numPosts", numOfPosts);
            ResultSet userSet = conn.createStatement().executeQuery(
                    "SELECT * FROM users "
                    + "WHERE user_id=" + user_id + ";");
            userSet.next();
            PrettyTime p = new PrettyTime();
            Timestamp stamp = userSet.getTimestamp("date_created");
            Date date = new Date(stamp.getTime());
            request.setAttribute("timeJoined", p.format(date));
        
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MyProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
}
