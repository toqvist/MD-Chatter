package com.toqvist.chatter


class AccountHandler {

    var activeUser: String? = null

    fun login (username: String, password: String): Boolean {
        if (username == "user1" && password == "password") {
            activeUser = "user1"
            return true
        }
        if (username == "user2" && password == "password") {
            activeUser = "user2"
            return true
        }

        return false
    }

}