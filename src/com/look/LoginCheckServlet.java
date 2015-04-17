/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author kevinholland
 */
@WebServlet("/authorizeLogin")
public class LoginCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session=request.getSession();  
        if (authenticateLogin(user, password)) {
            session.setAttribute("user", user);
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/login-retry.jsp").forward(request, response);
        }
    }
    
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
