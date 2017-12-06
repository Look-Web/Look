package com.look;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
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
 * LoginCheckServlet handles logging in.
 * - Verifies authentication
 * - Sets user session attribute
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
@WebServlet("/authorizeLogin")
public class LoginCheckServlet extends HttpServlet {
    /**
     * Processes post request and authenticates login
     * @param request HttpServletRequest login request sent from client
     * @param response HttpServletResponse to be sent to client
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session=request.getSession();  
        if (authenticateLogin(user, password)) {
            session.setAttribute("user", user);
            //no destinaton, default login
            if (session.getAttribute("destination") == null) {
                Logger.getLogger(LoginCheckServlet.class.getName()).info("No destination");
                //getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
                response.sendRedirect(".");
            } else { //destination given, user was redirected to login
                Logger.getLogger(LoginCheckServlet.class.getName()).info("dest");
                //getServletContext().getRequestDispatcher(session.getAttribute("destination").toString()).forward(request, response);
                response.sendRedirect(session.getAttribute("destination").toString());
                //get rid of destination
                session.setAttribute("destination", null);
            }
        } else {
            getServletContext().getRequestDispatcher("/login-retry.jsp").forward(request, response);
        }
    }
    
    /**
     * Authenticates login from user and password
     * @param user
     * @param password
     * @return Returns true if successful, false otherwise
     */
    private boolean authenticateLogin(String user, String password) {
        Connection conn = null;
        boolean authorized = false;
        try {
            conn = LookDatabaseUtils.getNewConnection();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginCheckServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            PreparedStatement loginCheckStatement = conn.prepareStatement("SELECT * FROM USERS "
                    + "WHERE username= ? AND "
                    + "pass= PASSWORD( ? ) ;");
            loginCheckStatement.setString(1, user);
            loginCheckStatement.setString(2, password);
            ResultSet rs = loginCheckStatement.executeQuery();
            authorized = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(LoginCheckServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return authorized;
    }
}
