package com.toqvist.chatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.formUsername)
        val password = findViewById<EditText>(R.id.formPassword)

        val loginButton = findViewById<Button>(R.id.buttonLogin)

        val accountManager = AccountManager()
        loginButton.setOnClickListener {

            val loginSuccess = accountManager.login(username.text.toString(),password.text.toString())

            if (loginSuccess) {
                val goToChat = Intent(this, ChatActivity::class.java)
                startActivity(goToChat)
            } else {
                val toast = Toast.makeText(this, "Login failed", Toast.LENGTH_LONG)
                toast.show()
            }

        }
    }
}