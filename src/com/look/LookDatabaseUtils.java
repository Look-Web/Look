/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class LookDatabaseUtils {
    public static final String DB = "look_db";
    public static final String DB_USER = "look_admin";
    public static final String DB_PASS = "lookpass";
    
    public static Connection getNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/" + DB, DB_USER, DB_PASS);
    }
    
    public static String getTagsFromPostID(int id) {
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet tagIDSet;
        String tags = "";
        try {
            tagIDSet = conn.createStatement().executeQuery(
                    "SELECT * FROM tags_has_posts "
                            + "WHERE posts_post_id=" + id + ";");
            //tagIDSet is now a set of all tags of post
            //iterate through these
            while (tagIDSet.next()) {
                int tag_id = tagIDSet.getInt(1);
                ResultSet tagSet = conn.createStatement().executeQuery(
                        "SELECT tag FROM tags WHERE tag_id=" + tag_id + ";");
                tagSet.next();
                String tag = tagSet.getString(1);
                tags += "#" + tag + " ";
            }
        } catch (SQLException ex) {
            Logger.getLogger(LookDatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tags;
    }
}
