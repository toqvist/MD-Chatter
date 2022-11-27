package com.toqvist.chatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var loggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(loggedIn) {
            val goToChat = Intent(this, ChatActivity::class.java)
            startActivity(goToChat)
        }

        val username = findViewById<EditText>(R.id.formUsername)
        val password = findViewById<EditText>(R.id.formPassword)

        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        val accountManager = AccountManager()
        loginButton.setOnClickListener {

            val loginSuccess = accountManager.login(username.text.toString(),password.text.toString())

            if (loginSuccess) {
                val goToChat = Intent(this, ChatActivity::class.java)
                startActivity(goToChat)
                loggedIn = true;
            } else {
                val toast = Toast.makeText(this, "Login failed", Toast.LENGTH_LONG)
                toast.show()
            }
        }

        registerButton.setOnClickListener {
            val goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean("loginStatus", loggedIn)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        loggedIn = savedInstanceState.getBoolean("loginStatus")

    }
}