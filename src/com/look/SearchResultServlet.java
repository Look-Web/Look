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
@WebServlet("/search")
public class SearchResultServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tag = request.getParameter("tag");
        if (tag.charAt(0) == '#') {
            tag = tag.substring(1);
        }
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
                        + "WHERE tags_tag_id=" + tag_id + " "
                        + "ORDER BY posts_post_id DESC;";
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
