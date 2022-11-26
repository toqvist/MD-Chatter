package com.toqvist.chatter

import android.widget.Toast


class AccountHandler {

    var activeUser: String? = null

    fun login (username: String, password: String): Boolean {
        if (username == "user1" && password == "password") {
            //String is immutable in kotlin, change later
            this.activeUser = "user1"
            return true
        }
        if (username == "user2" && password == "password") {
            this.activeUser = "user2"
            return true
        }

        return false
    }

    @JvmName("getActiveUser1")
    fun getActiveUser(): String? {
        return this.activeUser
    }

}