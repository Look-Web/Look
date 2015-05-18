package com.look;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * This class contains static methods concerning User data in the database
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
public class DatabaseUserUtils {
    /**
     * Gets logged in user from the session
     * @param session HttpSession associated with user
     * @return ResultSet of user, or null not logged in
     */
    public static ResultSet getLoggedInUser(HttpSession session) {
        Connection conn = null;
        if (session.getAttribute("user") == null) {
            return null;
        }
        String username = session.getAttribute("user").toString();
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet r = conn.createStatement().executeQuery("SELECT * FROM users WHERE username=\"" + username + "\";");
            r.next();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Gets logged in username
     * @param session HttpSession associated with user
     * @return String username
     */
    public static String getLoggedInUsername(HttpSession session) {
        return session.getAttribute("user").toString();
    }
    
    /**
     * Gets the first name of the user by username
     * @param username String username
     * @return String of username
     */
    public static String getFirstNameFromUsername(String username) {
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet r;
        try {
            r = conn.createStatement().executeQuery("SELECT first_name FROM users WHERE username='" + username + "';");
            if (r.next()) {
                return r.getString("first_name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Gets last name of user by username
     * @param username HttpSession associated with user
     * @return String of last name
     */
    public static String getLastNameFromUsername(String username) {
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet r;
        try {
            r = conn.createStatement().executeQuery("SELECT last_name FROM users WHERE username='" + username + "';");
            if (r.next()) {
                return r.getString("last_name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Gets username from the user's id
     * @param id ID of user
     * @return String of username
     */
    public static String getUsernameFromUserID(int id) {
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet r;
        try {
            r = conn.createStatement().executeQuery("SELECT username FROM users WHERE user_id='" + id + "';");
            if (r.next()) {
                return r.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Gets userID from username
     * @param username String username of user
     * @return String of userID
     */
    public static int getUserIDFromUsername(String username) {
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet r;
        try {
            r = conn.createStatement().executeQuery("SELECT user_id FROM users WHERE username='" + username + "';");
            if (r.next()) {
                return r.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseUserUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }  
}
