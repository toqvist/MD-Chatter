package com.toqvist.chatter

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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

        addMessageToChat("hey", "suer", chatHistory)


        sendMessageButton.setOnClickListener {
            val message = messageForm.text.toString()
            //val sender = accountHandler.getActiveUser().toString()
            val sender = "testuser"

            //addMessageToChat(message, sender, chatHistory)
            sendMessage(message,sender)

            //val toast = Toast.makeText(this, sender, Toast.LENGTH_LONG)
            //toast.show()
        }
    }

    fun addMessageToChat(message: String, sender: String, chatHistory: ChipGroup) {

        val newChip = Chip(this)
        newChip.setText(sender + " : " + message)

        chatHistory?.addView(newChip)
    }

    private fun sendMessage(message: String, sender: String) {
        val chatMessage = hashMapOf(
            "text" to message,
            "user" to sender
        )

        val db = Firebase.firestore
        db.collection("messages")
            .add(chatMessage)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val toast = Toast.makeText(this, "Submitted", Toast.LENGTH_LONG)
                toast.show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                val toast = Toast.makeText(this, "Failed", Toast.LENGTH_LONG)
                toast.show()
            }
    }
}