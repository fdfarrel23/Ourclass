package com.example.ourclass.model

object AppVar {
    val LOGIN_URL = "http://fdfarrel23.epizy.com/API.php"

    //Keys for email and password as defined in our $_POST['key'] in login.php
    val KEY_USERNAME = "username"
    val KEY_PASSWORD = "password"

    //If server response is equal to this that means login is successful
    val LOGIN_SUCCESS = "success"

}
