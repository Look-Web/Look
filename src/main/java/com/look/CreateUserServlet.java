package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

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
 * CreateUserServlet handles the creating of users
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
    //logger for debuggin
    Logger log = Logger.getLogger(CreateUserServlet.class.getName());
    //boolean for stopping on bad input
    private boolean stop = false;
    
    /**
     * Processes post and adds user to database if successful
     * @param request
     * @param response 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        stop = false;
        Connection conn = null;
        String message;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CreateUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        if (username == null) {
            message = "Please enter a username";
            stop(request, response, message);
        } else if (password == null) {
            message = "Please enter a password";
            stop(request, response, message);
        } else if (repeatPassword == null) {
            message = "Please enter your password again";
            stop(request, response, message);
        } else if (firstName == null) {
            message = "Please enter your first name";
            stop(request, response, message);
        } else if (lastName == null) {
            message = "Please enter your last name";
            stop(request, response, message);
        }
        
        if (username != null) {
            if (username.length() < 5) {
                message = "Username must be at least 5 characters";
                stop(request, response, message);
            }
            if (!StringUtils.isAlphanumeric(username)) {
                message = "Username must be alpha numeric";
                stop(request, response, message);
            }
        }
        
        try {
            PreparedStatement usernameStatement = conn.prepareStatement(
                    "SELECT * FROM users "
                    + "WHERE username= ? ;");
            usernameStatement.setString(1, username);
            ResultSet r = usernameStatement.executeQuery();
            if (r.next()) {
                message = "The desired username already exists";
                stop(request, response, message);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (password != null) {
            if (password.length() < 8) {
                message = "Password must be at least 8 characters in length";
                stop(request, response, message);
            } 
            if (!StringUtils.isAsciiPrintable(password)) {
                message = "Password has invalid characters. Please try again.";
                stop(request, response, message);
            }
            if (repeatPassword != null && !password.equals(repeatPassword)) {
                message = "Given passwords do not match. Try again.";
                stop(request, response, message);
            }
        }
        
        if (stop) {
            return;
        }
        
        try {
            String createUserSQL = "INSERT INTO users (user_id, username, pass, first_name, last_name) "
                + "VALUES (?, ?, PASSWORD(?), ?, ?);";
            PreparedStatement createUserStatement = conn.prepareStatement(createUserSQL);
            createUserStatement.setString(2, username);
            createUserStatement.setString(3, password);
            createUserStatement.setString(4, firstName);
            createUserStatement.setString(5, lastName);
            
            Random r = new Random();
            int user_id = 0;
            while (user_id == 0) {
                int temp_user_id = r.nextInt(10000000);
                ResultSet tempResult = conn.createStatement().executeQuery(
                        "SELECT * FROM users "
                        + "WHERE user_id=" + temp_user_id + ";");
                if (!tempResult.next()) {
                    user_id = temp_user_id;
                }
            }
            createUserStatement.setInt(1, user_id);
            
            createUserStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CreateUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //it worked!!! forward to index jsp
        Logger.getLogger(CreateUserServlet.class.getName()).info("Account created");
        request.getSession().setAttribute("user", username);
        try {
            request.getSession().setAttribute("account_created", "yes");
            response.sendRedirect("thanks-join.jsp");
        } catch (IOException ex) {
            Logger.getLogger(CreateUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Stops adding user and forwards error message with request back to user
     * @param request
     * @param response
     * @param message 
     */
    private void stop(HttpServletRequest request, HttpServletResponse response, String message) {
        stop = true;
        request.setAttribute("message", message);
        try {
            request.getRequestDispatcher("/createAccount.jsp").forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(CreateUserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
