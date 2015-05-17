/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("deleting") == null) {
            response.sendRedirect(".");
        } else {
            session.setAttribute("deleting", null);
        }
        
        int post_id = Integer.parseInt(request.getParameter("id"));
        
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
            conn.createStatement().executeUpdate(
                    "DELETE FROM posts "
                    + "WHERE post_id=" + post_id + ";");
            conn.createStatement().executeUpdate(
                    "DELETE FROM tags_has_posts "
                    + "WHERE posts_post_id=" + post_id + ";");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeletePostServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DeletePostServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        response.sendRedirect(".");
    }
}
