package com.toqvist.chatter

import android.widget.Toast


open class AccountManager {

    //BUG: activeUser does not update with the setActiveUser. The reference should update but it doesn't
    open var activeUser: Account = Account("DefaultUser")

    fun login (username: String, password: String): Boolean {
        if (username == "user1" && password == "password") {
            setActiveUser("user1")
            return true
        }
        if (username == "user2" && password == "password") {
            setActiveUser("user2")
            return true
        }

        return false
    }

    fun register (username: String, password: String): Boolean {
        if (username == "user1") {
            setActiveUser("user1")
            return false
        }
        if (username == "user2") {
            setActiveUser("user2")
            return false
        }

        return true
    }

    fun setActiveUser(username: String) {
        activeUser = Account(username)
    }

    fun getActiveUser(): String {
        return this.activeUser.username
    }

}

data class Account(val username: String) {
    val user: String = username
}