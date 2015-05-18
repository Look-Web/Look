package com.look;

import com.sun.istack.logging.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
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
 * This class contains a static method for generating HTML for the MenuBar
 * 
 * @author  Kevin Holland (GitHub: kholland950)
 * @date    04/20/15
 * @updated 05/17/15
 */
public class MenuBar {
    /**
     * Generates HTML for MenuBar from given session and activeItem
     * @param session HttpSession corresponding with user's session
     * @param activeItem String of active page or currently selected menu item
     * @return Sting HTML of menu bar
     */
    public static String generateMenuBar(HttpSession session, Items activeItem) {
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
        
        //if user logged in, show Hello item and menu
        boolean loggedIn;
        if (session.getAttribute("user") != null) {
            loggedIn = true;
            userItemHtml += "<li class='%s has-dropdown'><a class='hvr-underline-from-center' href='#'>Hello, ";
            userItemHtml += DatabaseUserUtils.getFirstNameFromUsername(session.getAttribute("user").toString());
            userItemHtml += "!</a><ul class='dropdown'><li class='%s'><a class='hvr-underline-from-center' href='myProfile'>Profile</a></li>";
            userItemHtml += "<li class='%s'><a class='hvr-underline-from-center' href='account.jsp'>Account Settings</a></li>";
            userItemHtml += "<li><a class='hvr-underline-from-center' href='logout.jsp'>Logout</a></li></ul></li>";
        } else { //else show login item
            loggedIn = false;
            userItemHtml += "<li class='%s'><a class='hvr-underline-from-center' href='login.jsp' data-reveal-id='loginModal'>Login | Sign up</a></li>";
        }
        //add all right section items in to right section html for string substitution
        rightSectionHeaderHtml += searchBarHtml + recentFeedItemHtml + uploadItemHtml + userItemHtml;
        //string substitution list
        List<String> subs = new ArrayList<>();
        //3 items default
        int numItems = 3;
        if (loggedIn) { //5 items when logged in
            numItems = 5;
        } //set all to inactive as default
        for (int i = 0; i < numItems; i++) {
            subs.add("inactive");
        }
        //if active item is not NONE, then set the proper one to "active"
        if (!activeItem.equals(Items.NONE)) {
            subs.set(activeItem.value, "active");
            //if logged in, handle the special case of account and profile
            if (loggedIn) {
                if (activeItem.equals(Items.USER_ACCOUNT) || activeItem.equals(Items.USER_PROFILE)) {
                    //USER should also be active when account or profile is active
                    subs.set(Items.USER.value, "active");
                }
            }
        }
        //format string using subs (active and inactive)
        rightSectionHeaderHtml = String.format(rightSectionHeaderHtml, subs.toArray());
        
        //return generated html
        return leftSectionHeaderHtml + logonHtml + rightSectionHeaderHtml + closingHtml;
    }
    
    /**
     * This enumeration represents all possible active Items on the menu bar
     */
    public enum Items {
        NONE(-1), RECENT_FEED(0), UPLOAD(1), USER(2), USER_PROFILE(3), USER_ACCOUNT(4);
        
        public final int value;
        private Items(int value) {
            this.value = value;
        }
    }
}
