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
