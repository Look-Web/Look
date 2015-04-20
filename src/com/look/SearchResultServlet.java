/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kevinholland
 */
@WebServlet("/search")
public class SearchResultServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("tag");
        Connection conn = null;
        
        request.setAttribute("searchTag", "#" + tag);
        
        //post_id's separated by spaces
        String postIDString = "";
        
        try {
            conn = LookDatabaseUtils.getNewConnection();
            
            ResultSet r = conn.createStatement().executeQuery(
                    "SELECT tag_id FROM tags where tag='" + tag + "';");
            if (r.next()) {
                int tag_id = r.getInt(1);
                String postIDSQL ="SELECT posts_post_id FROM tags_has_posts "
                        + "WHERE tags_tag_id=" + tag_id + ";";
                ResultSet postIDSet = conn.createStatement().executeQuery(postIDSQL);
                while (postIDSet.next()) {
                    int postID = postIDSet.getInt(1);
                    //id of post to display
                    postIDString += postID + " ";
                }
            }
            
            request.setAttribute("postIDs", postIDString);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SearchResultServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SearchResultServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.getRequestDispatcher("/searchresult.jsp").forward(request, response);
    }
}
