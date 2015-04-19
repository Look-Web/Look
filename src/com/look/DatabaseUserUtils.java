package com.look;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevinholland
 */
public class DatabaseUserUtils {
    
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
    
    public static String getLoggedInUsername(HttpSession session) {
        return session.getAttribute("user").toString();
    }
    
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
}
