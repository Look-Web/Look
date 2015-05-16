/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.look;

import javax.servlet.http.HttpSession;

/**
 *
 * @author kevinholland
 */
public class MenuBar {
    public static String generateMenuBar(HttpSession session, String activeItem) {
        String leftSectionHeaderHtml = 
                "<div class='contain-to-grid'>" +
                "   <nav class='top-bar' data-topbar data-options='is_hover: false' role='navigation'>";
        String logonHtml = 
                "<ul class='title-area'>" +
                "   <li class='name'>" +
                "       <h1><a href='.'>Look!</a></h1>" +
                "   </li>" +
                "   <li class='toggle-topbar menu-icon'><a href='#'><span>Menu</span></a></li>" +
                "</ul>";
                
        String rightSectionHeaderHtml = 
                "<section class='top-bar-section'>" +
                "   <ul class='right'>";
        String searchBarHtml = 
                "<li class='has-form'>" +
                "   <form method='GET' action='search'>" +
                "       <div class='row'>" +
                "           <input class='search-box' type='text' name='tag' placeholder='Search by tag here'>" +
                "       </div>" +
                "   </form>" +
                "</li>";
        String recentFeedItemHtml = 
                "<li class='%s'><a class='hvr-underline-from-center' href='.'>Recent Feed</a></li>";
        String uploadItemHtml = 
                "<li class='%s'> <a class='hvr-underline-from-center' href='upload.jsp'>Upload an Image</a></li>";
        String userItemHtml = "";
        String closingHtml = 
                "           </ul>" +
                "       </section>" +
                "   </nav>" +
                "</div>";
        
        if (session.getAttribute("user") != null) {
            userItemHtml += "<li class='%s has-dropdown'><a class='hvr-underline-from-center' href='#'>Hello, ";
            userItemHtml += DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString());
            userItemHtml += "!</a><ul class='dropdown'><li class='%s'><a class='hvr-underline-from-center' href='myProfile'>Profile</a></li>";
            userItemHtml += "<li class='%s'><a class='hvr-underline-from-center' href='account.jsp'>Account Settings</a></li>";
            userItemHtml += "<li><a class='hvr-underline-from-center' href='logout.jsp'>Logout</a></li></ul></li>";
        } else {
            userItemHtml += "<li class='%s'><a class='hvr-underline-from-center' href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>";
        }
        
        if (activeItem.equals("Recent Feed")) {
            recentFeedItemHtml = String.format(recentFeedItemHtml, "active");
        } else if (activeItem.equals("Upload")) {
            uploadItemHtml = String.format(uploadItemHtml, "active");
        } else if (activeItem.contains("User")) {
            userItemHtml = String.format(userItemHtml, "active", "%s", "%s");
            if (activeItem.equals("User:Profile")) {
                userItemHtml = String.format(userItemHtml, "active", "");
            }
            else if (activeItem.equals("User:Account")) {
                userItemHtml = String.format(userItemHtml, "", "active");
            }
        }
        
        return leftSectionHeaderHtml + logonHtml + rightSectionHeaderHtml + 
                searchBarHtml + recentFeedItemHtml + uploadItemHtml + 
                userItemHtml + closingHtml;
    }
}
