package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
 * DeleteAccountServlet handles deletion of account
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
    /**
     * Processes get request to delete account
     * @param request
     * @param response
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //select post_id from posts where user_id=123
        //delete from posts where user_id=123
        //while next postID
        //  delete from tags_has_posts where post_id=postID
        //delete from users where user_id=123
        
        String username = request.getSession().getAttribute("user").toString();
        int user_id = DatabaseUserUtils.getUserIDFromUsername(username);
        
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
            //get list of post_id's
            List<Integer> postIDList = new ArrayList<Integer>();
            ResultSet idSet = conn.createStatement().executeQuery(
                    "SELECT post_id from posts where users_user_id=" + user_id +";");
            while (idSet.next()) {
                postIDList.add(idSet.getInt(1));
            }
            //now have list of post IDs posted by the user
            //delete them now
            conn.createStatement().executeUpdate(
                    "DELETE FROM posts WHERE users_user_id=" + user_id + ";");
            
            //go through saved postIDs and delete them from tags_has_posts;
            for (int id : postIDList) {
                conn.createStatement().executeUpdate(
                        "DELETE FROM tags_has_posts WHERE posts_post_id=" + id +";");
            }
            
            conn.createStatement().executeUpdate("DELETE FROM users where user_id=" + user_id + ";");
            //USER FULLY DELETED
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DeleteAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        session.setAttribute("account_deleted", true);
        response.sendRedirect("account-deleted.jsp");
    }
}
