/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author kevinholland
 */
@WebServlet("/uploadServlet")
public class UploadFileServlet extends HttpServlet {
    private final String IMAGE_DIRECTORY = "images/";
    private String db = "look_db";
    private String dbUser = "look_admin";
    private String dbPass = "lookpass";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        logRequest(Logger.getLogger(UploadFileServlet.class.getName()), request);
        
        String user = request.getParameter("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");
        String imageURL = null;
                 
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
         
        try {
            // connects to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+db, dbUser, dbPass);
 
            // constructs SQL statement
            String sql = "INSERT INTO posts (post_id, title, description, users_user_ID, image_url, time_posted)"
                    + " values (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
            PreparedStatement statement = conn.prepareStatement(sql);
            Random r = new Random();
            int id = r.nextInt(Integer.MAX_VALUE);
            statement.setInt(1, id);
            statement.setString(2, title);
            statement.setString(3, description);
            
            String userQuery = "SELECT user_id FROM users WHERE username=" + user;
            Statement userStatement = conn.createStatement();
            ResultSet userIDRS = userStatement.executeQuery(userQuery);
            userIDRS.next();
            int userID = userIDRS.getInt(1);
            statement.setInt(4, userID);
            
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            String name = new File(item.getName()).getName();
                            imageURL = IMAGE_DIRECTORY + File.separator + name;
                            item.write(new File(imageURL));
                        }
                    }
                } catch (FileUploadException ex) {
                    Logger.getLogger(UploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(UploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                request.setAttribute("message", "File upload request not found");
            }
            statement.setString(5, imageURL);
            //TODO save tags
            statement.executeUpdate();
            
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadFileServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            //TODO Implement upload success message
            // sets the message in request scope
            request.setAttribute("Message", message);
             
            // forwards to the message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        }
    }
    
    private void logRequest(Logger logger, HttpServletRequest request) {
        logger.info("---------------------");
        logger.info("Request sent.");
        logger.info(request.getHeader("Referer"));
        logger.info("---Parameters in request: ");
        Enumeration paramaterNames = request.getParameterNames();
        if (!paramaterNames.hasMoreElements()) {
            logger.info("None");
        } else {
            while(paramaterNames.hasMoreElements() ) {
                logger.info(paramaterNames.nextElement().toString());
            }
        }
        logger.info("--------------");
        
        String user = request.getParameter("user");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String tags = request.getParameter("tags");
        String imageURL = null;
        
        logger.info("User: " + user);
        logger.info("Title: " + title);
        logger.info("Description: " + description);
        logger.info("Tags: " + tags);
        
        logger.info("End request");
        logger.info("---------------------");
    }
}

