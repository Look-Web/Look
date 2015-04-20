/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import static org.apache.commons.lang3.StringUtils.split;

/**
 *
 * @author kevinholland
 */
@WebServlet("/uploadServlet")
public class UploadPostServlet extends HttpServlet {
    Logger log = Logger.getLogger(UploadPostServlet.class.getName());
    private String IMAGE_DIRECTORY = null;
    private final String db = "look_db";
    private final String dbUser = "look_admin";
    private final String dbPass = "lookpass";
    
    private final String USER_FIELD_NAME = "user";
    private final String TITLE_FIELD_NAME = "title";
    private final String DESCRIPTION_FIELD_NAME = "description";
    private final String TAGS_FIELD_NAME = "tags";
    
    private String user = null;
    private String title = null;
    private String description = null;
    private String tags = null;
    private List<String> tagList;
    private String imageURL = null;
    
    private int post_id;
    
    private String message = "";
    
    private String imageExtension = null;
    
    private InputStream fileContent;
        
 //-----------------------------------------------------------------------------------------------

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {    
        message = "";
        URL imagesResourceURL = UploadPostServlet.class.getClassLoader().getResource("images/");
        
        IMAGE_DIRECTORY = getServletContext().getRealPath("/images/");
        
        user = request.getSession().getAttribute("user").toString();
        
        boolean success = handleRequest(request);
        if (success == false) {
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("tags", tags);
            request.setAttribute("message", message);
            getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
         
        try {
            // connects to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+db, dbUser, dbPass);
 
            // constructs SQL statement
            String postSQL = "INSERT INTO posts (title, description, users_user_ID, image_url, time_posted)"
                    + " values (?, ?, ?, ?, CURRENT_TIMESTAMP)";
            PreparedStatement post_statement = conn.prepareStatement(postSQL);
            post_statement.setString(1, title);
            post_statement.setString(2, description);
            
            String userQuery = "SELECT user_id FROM users WHERE username=\"" + user + "\"";
            Statement userStatement = conn.createStatement();
            ResultSet userIDRS = userStatement.executeQuery(userQuery);
            userIDRS.next();
            int userID = userIDRS.getInt(1);
            post_statement.setInt(3, userID);
            
            post_statement.setString(4, imageURL);
            post_statement.executeUpdate();
            
            Statement postIDStatement = conn.createStatement();
            ResultSet postIDRS = postIDStatement.executeQuery("SELECT post_id FROM posts WHERE image_url=\"" + imageURL + "\";");
            postIDRS.next();
            post_id = postIDRS.getInt(1);
            imageURL = FilenameUtils.getName(post_id + "." + imageExtension);
            String imagePermaSQL = "UPDATE posts "
                    + "SET image_url= ? "
                    + "WHERE post_id=" + post_id + ";";
            PreparedStatement imagePermaURL = conn.prepareStatement(imagePermaSQL);
            imagePermaURL.setString(1, imageURL);
            imagePermaURL.executeUpdate();
            
            if (!uploadFile()) {
                request.setAttribute("message", "Failed to upload");
                Statement removeStatement = conn.createStatement();
                removeStatement.executeUpdate("DELETE FROM posts WHERE post_id=" + post_id + ";");
                getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
                return;
            }
            
            //if there are tags
            if (tagList != null && tagList.size() > 0) {
                //add tags to database
                String addTagSQL = "INSERT IGNORE INTO tags (tag) " +
                    "VALUES (?); ";
                String addRelationshipSQL = "INSERT INTO tags_has_posts(tags_tag_id, posts_post_id) " +
                    "VALUES (?, ?);";
                for (String tag : tagList) {
                    PreparedStatement s = conn.prepareStatement(addTagSQL);
                    s.setString(1, tag);
                    s.executeUpdate();
                    ResultSet r = conn.createStatement().executeQuery(
                            "SELECT tag_id FROM tags WHERE tag='" + tag + "';");
                    r.next();
                    int tag_id = r.getInt(1);
                    PreparedStatement rS = conn.prepareStatement(addRelationshipSQL);
                    rS.setInt(1, tag_id);
                    rS.setInt(2, post_id);
                    rS.executeUpdate();
                }
            }
            
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadPostServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
             
            //TODO change this to go to the image post
            response.sendRedirect("post?id=" + post_id);
            //getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
    
    
    private boolean uploadFile() {
        File f = new File(IMAGE_DIRECTORY + File.separator + imageURL);
        OutputStream out = null;
        try {
            out = new FileOutputStream(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UploadPostServlet.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            IOUtils.copy(fileContent,out);
            fileContent.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(UploadPostServlet.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
//-----------------------------------------------------------------------------------------------
    
    private boolean handleRequest(HttpServletRequest request) {
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                
                boolean success = true;
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {                            
                        // Process form file field (input type="file").
                        String fieldName = item.getFieldName();
                        String filename = item.getName();
                        if (filename.equals("")) {
                            //only update message if there isn't one yet
                            if (message.equals("")) {
                                message = "Please choose an image";
                            }
                            success = false;
                        }
                        String clientFileName = FilenameUtils.getName("TEMP_"+item.getName().replaceAll(" ", ""));

                        imageExtension = FilenameUtils.getExtension(clientFileName);
                        imageURL = clientFileName + "." + imageExtension;
                        fileContent = item.getInputStream();
                    } else {
                        String fieldName = item.getFieldName();
                        String value = item.getString();
                        //only title is required
                        if (value.equals("") && (fieldName.equals("title"))) {
                            message = "Please enter a title";
                            success = false;
                        }
                        if (fieldName.equals(TITLE_FIELD_NAME)) {
                            title = value;
                        } else if (fieldName.equals(DESCRIPTION_FIELD_NAME)) {
                            description = value;
                        } else if (fieldName.equals(TAGS_FIELD_NAME)) {
                            tags = value;
                            if (!tags.equals("")) {
                                tagList = new LinkedList<String>(Arrays.asList(tags));
                                for (int i = 0; i < tagList.size(); i++) {
                                    String tag = tagList.get(i);
                                    if (tag.charAt(0) != '#') {
                                        if (message.equals("")) {
                                            message = "Tags must begin with #";
                                        }
                                        tagList.remove(i);
                                    } else if (!StringUtils.isAlphanumeric(tag.substring(1))) {
                                        if (message.equals("")) {
                                            message = "Tags must only contain numbers and letters";
                                        }
                                        tagList.remove(i);
                                    } else if (tag.length() > 20) {
                                        if (message.equals("")) {
                                            message = "Tags must be 20 characters or less";
                                        }
                                        tagList.remove(i);
                                    } else {
                                        //tag is valid, remove the '#' for storage
                                        tagList.set(i, tag.substring(1));
                                    }   
                                }
                            }
                            //now have list of alphanumeric tags of 20 chars or less
                        }
                    }
                }
                if (!success) {
                    return false;
                }
            } catch (FileUploadException ex) {
                Logger.getLogger(UploadPostServlet.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (Exception ex) {
                Logger.getLogger(UploadPostServlet.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
                request.setAttribute("message", "File upload request not found");
                return false;
        }
       
        return true;
    }
}

