package com.look;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * UploadPostServlet handles the uploading of posts to both the Look server and database.
 * The uploaded image is stored in the build directory /images and a reference
 * to the image is stored in the database. The image name is changed to the
 * postID and the associated extension of the image. 
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/25/2015
 * @updated 05/17/2015
 */
@WebServlet("/uploadServlet")
public class UploadPostServlet extends HttpServlet {
    //logger for debugging requests
    static final Logger log = Logger.getLogger(UploadPostServlet.class.getName());
    
    //directory for storing images 
    private String IMAGE_DIRECTORY = null;
    
    //constants for DB field names
    private static final String TITLE_FIELD_NAME = "title";
    private static final String DESCRIPTION_FIELD_NAME = "description";
    private static final String TAGS_FIELD_NAME = "tags";
    
    //variables for storing data about the post
    private String user = null;
    private String title = null;
    private String description = null;
    private String tags = null;
    private List<String> tagList;
    private String imageURL = null;
    //image extensions from uploaded image
    private String imageExtension = null;
    //image content
    private InputStream fileContent;
        
    //responseMessage from server.
    private String responseMessage = "";
    
 //-----------------------------------------------------------------------------------------------

    /**
     * Handles post request for uploading images and creating a post (image post)
     * @param request HttpRequest from client
     * @param response HttpResponse to be sent to client
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {   
        //must reset responseMessage on next post. Fixes unwanted error message bug
        responseMessage = "";
        
        //get real image directory path
        IMAGE_DIRECTORY = getServletContext().getRealPath("/images/");
        
        //get user attribute from session
        user = request.getSession().getAttribute("user").toString();
        
        //try to handle request
        boolean success = handleRequest(request);
        //if handling was not successful (bad input) forward to upload.jsp with message
        if (success == false) {
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("tags", tags);
            request.setAttribute("message", responseMessage);
            getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
            return;
        }
        
        Connection conn = null; // connection to the database
        
        int post_id = -1;
         
        try {
            // connects to the database
            conn = LookDatabaseUtils.getNewConnection();
 
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
            responseMessage = "ERROR: " + ex.getMessage();
            log.warning(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadPostServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    log.warning(ex.getMessage());
                }
            }
             
            response.sendRedirect("post?id=" + post_id);
            //getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
    
    /***************************************************************************
     * Saves user uploaded file to server
     * @return True if successfully stored on server, false otherwise
     */
    private boolean uploadFile() {
        File f = new File(IMAGE_DIRECTORY + File.separator + imageURL);
        OutputStream out;
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
    
    /***************************************************************************
     * Processes request data and saves into instance variables
     * @param request HttpServletRequest from client
     * @return True if successfully handled, false otherwise
     */
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
                            //only update responseMessage if there isn't one yet
                            if (responseMessage.equals("")) {
                                responseMessage = "Please choose an image";
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
                            responseMessage = "Please enter a title";
                            success = false;
                        }
                        switch (fieldName) {
                            case TITLE_FIELD_NAME:
                                title = value;
                                break;
                            case DESCRIPTION_FIELD_NAME:
                                description = value;
                                break;
                            case TAGS_FIELD_NAME:
                                tags = value;
                                if (!tags.equals("")) {
                                    tagList = new LinkedList<>(Arrays.asList(tags.split(" ")));
                                    for (int i = 0; i < tagList.size(); i++) {
                                        String tag = tagList.get(i);
                                        if (tag.charAt(0) != '#') {
                                            if (responseMessage.equals("")) {
                                                responseMessage = "Tags must begin with #";
                                                success = false;
                                            }
                                            tagList.remove(i);
                                        } else if (!StringUtils.isAlphanumeric(tag.substring(1))) {
                                            log.info(tag.substring(1));
                                            if (responseMessage.equals("")) {
                                                responseMessage = "Tags must only contain numbers and letters";
                                                success = false;
                                            }
                                            tagList.remove(i);
                                        } else if (tag.length() > 20) {
                                            if (responseMessage.equals("")) {
                                                responseMessage = "Tags must be 20 characters or less";
                                                success = false;
                                            }
                                            tagList.remove(i);
                                        } else {
                                            //tag is valid, remove the '#' for storage
                                            tagList.set(i, tag.substring(1));
                                        }
                                    }   
                                }
                                //now have list of alphanumeric tags of 20 chars or less
                                break;
                        }
                    }
                }
                if (!success) {
                    return false;
                }
            } catch (FileUploadException | IOException ex) {
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

