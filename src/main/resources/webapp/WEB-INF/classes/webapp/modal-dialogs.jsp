<%-- 
    Document   : modal-dialogs
    Created on : May 15, 2015, 11:40:39 PM
    Author     : kevinholland
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--MODAL BOXES-->
<div id="loginModal" class="reveal-modal small login-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
    <form action="authorizeLogin" method="post">
        <div class="row">
            <div class="small-12 columns">
                <div class="row" style="text-align: center">
                    <h4>Already have an account? Log in here.</h4>
                </div>
                <div class="row">
                    <input type="text" name="username" placeholder="Username"> 
                </div>
                <div class="row">
                    <input type="password" name="password" placeholder="Password">
                </div>
                <div class="row" style="text-align: center;">
                    <input type="submit" value="Login" class="button"> 
                </div>
                <div class="row" style="text-align: center;">
                    Don't have an account? <br /><a href="createAccount.jsp" data-reveal-id='registerModal'>Sign up here!</a>
                </div>
            </div>
        </div>
    </form>
    <a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>

<div id="registerModal" class="reveal-modal medium" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
    <form action="createUser" method="post">
        <div class="row">
            <div class="small-12 columns">
                <div class="row" style="text-align: center">
                    <h2>Register</h2>
                </div>
                <div class="row">
                    <label>
                        <input type="text" name="username" placeholder="Username"> 
                    </label>
                </div>
                <div class="row">
                    <label>
                        <input type="password" name="password" placeholder="Password">
                    </label>
                </div>
                <div class="row">
                    <label>
                        <input type="password" name="repeatPassword" placeholder="Repeat Password">
                    </label>
                </div>
                <div class="row">
                    <label>
                        <input type="text" name="firstName" placeholder="First Name">
                    </label>
                </div>
                <div class="row">
                    <label>
                        <input type="text" name="lastName" placeholder="Last Name">
                    </label>
                </div>
                <div class="row" style="text-align: center;">
                    <input type="submit" value="Register" class="button"> 
                </div>
            </div>
        </div>
    </form>
    <a class="close-reveal-modal" aria-label="Close">&#215;</a>
</div>
