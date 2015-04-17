/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
