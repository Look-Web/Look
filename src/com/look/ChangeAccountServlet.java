/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
 *
 * @author kevinholland
 */
@WebServlet("/changeAccount")
public class ChangeAccountServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newFirstName = request.getParameter("firstName");
        String newLastName = request.getParameter("lastName");
        
        if (!StringUtils.isAlpha(newFirstName)) {
            request.setAttribute("message", "First name cannot contain numbers or special characters");
            request.getRequestDispatcher("/account.jsp").forward(request, response);
        } else if (!StringUtils.isAlpha(newLastName)) {
            request.setAttribute("message", "Last name cannot contain numbers or special characters");
            request.getRequestDispatcher("/account.jsp").forward(request, response);
        }
        
        String username = request.getSession().getAttribute("user").toString();
        String oldFirstName = DatabaseUserUtils.getFirstNameFromUsername(username);
        String oldLastName = DatabaseUserUtils.getLastNameFromUsername(username);
        
        if (newFirstName.equals(oldFirstName) || newLastName.equals(oldLastName)) {
            response.sendRedirect("account.jsp");
        }
        
        //change the name
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
            PreparedStatement updateStatement = conn.prepareStatement(
                "UPDATE users "
                + "SET first_name=?, "
                + "last_name=? "
                + "WHERE username=?");
            updateStatement.setString(1, newFirstName);
            updateStatement.setString(2, newLastName);
            updateStatement.setString(3, username);
            updateStatement.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChangeAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ChangeAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
