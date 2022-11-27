package com.toqvist.chatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.formUsername)
        val password = findViewById<EditText>(R.id.formPassword)

        val loginButton = findViewById<Button>(R.id.buttonRegister)

        val accountManager = AccountManager()

        loginButton.setOnClickListener {

            val registerSuccess = accountManager.register(username.text.toString(),password.text.toString())

            if (registerSuccess) {
                val toast = Toast.makeText(this, "Registered! (Not really)", Toast.LENGTH_LONG)
                toast.show()

                val goToChat = Intent(this, MainActivity::class.java)
                startActivity(goToChat)
            } else {
                val toast = Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG)
                toast.show()
            }

        }
    }
}