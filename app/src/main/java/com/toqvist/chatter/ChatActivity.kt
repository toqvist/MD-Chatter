package com.toqvist.chatter

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class ChatActivity : AppCompatActivity() {

//    val chatHistory = findViewById<ChipGroup>(R.id.chatHistory)
    var chatMessages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatHistory = findViewById<ChipGroup>(R.id.chatHistory)

        val accountHandler = AccountHandler()

        val messageForm = findViewById<EditText>(R.id.messageForm)
        val sendMessageButton = findViewById<Button>(R.id.buttonSend)

        addMessage("hello", "user", chatHistory)

        sendMessageButton.setOnClickListener {
            val message = messageForm.text.toString()
            val sender = accountHandler.getActiveUser().toString()


            addMessage(message, sender, chatHistory)

            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
            toast.show()
        }
    }

    fun addMessage(message: String, sender: String, chatHistory: ChipGroup) {

        val newChip = Chip(this)
        //newChip.setText(sender + " : " + message)
        newChip.setText(message)

        chatHistory?.addView(newChip)
    }
}