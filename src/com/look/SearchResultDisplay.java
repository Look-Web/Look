/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevinholland
 */
public class SearchResultDisplay {
    
    public static String displaySearch(String postIDs) {
        if (postIDs.equals("")) {
            //no results were found
            return "";
        }
        String output = "";
        List<String> postIDList = Arrays.asList(postIDs.split(" "));
        //TODO any initialization to the output
        
        //results were found, display posts
        Connection conn = null;
        try {
            conn = LookDatabaseUtils.getNewConnection();
            
            for (String postID : postIDList) {
                int post_id = Integer.parseInt(postID);
                ResultSet post = conn.createStatement().executeQuery(
                        "SELECT * FROM posts WHERE post_id=" + post_id + ";");
                post.first();
                output += displayPost(post);
            }
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SearchResultDisplay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return output;
    }
    
    private static String displayPost(ResultSet post) throws SQLException {
        String postOutput = "";
        postOutput += post.getString("title") + "<br/>";
        postOutput += post.getString("description") + "<br/>";
        int user_id = post.getInt("users_user_id");
        postOutput += DatabaseUserUtils.getUsernameFromUserID(user_id) + "<br/>";
        postOutput += LookDatabaseUtils.getTagsFromPostID(post.getInt("post_id")) + "<br/>";
        postOutput += post.getString("time_posted") + "<br/>";
        postOutput += "<img src='images/" + post.getString("image_url") + "'/><br/>";
        return postOutput;
    }
}
