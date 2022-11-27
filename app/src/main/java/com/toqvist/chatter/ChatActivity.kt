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
import java.util.*


open class ChatActivity : AppCompatActivity() {

    open var chatMessages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val chatHistory = findViewById<ChipGroup>(R.id.chatHistory)

        val accountManager = AccountManager()

        val messageForm = findViewById<EditText>(R.id.messageForm)
        val sendMessageButton = findViewById<Button>(R.id.buttonSend)

        getMessages(chatHistory)

        sendMessageButton.setOnClickListener {
            val message = messageForm.text.toString()
            val sender = accountManager.getActiveUser()

            sendMessage(message,sender)
        }
    }

    fun addMessageToChat(message: String, sender: String, chatHistory: ChipGroup) {

        val newChip = Chip(this)
        newChip.setText(sender + " : " + message)

        chatHistory?.addView(newChip)
    }

    private fun sendMessage(message: String, sender: String) {

        chatMessages++
        val chatMessage = hashMapOf(
            "text" to message,
            "user" to sender,
        )

        val db = Firebase.firestore

        db.collection("messages").document(chatMessages.toString())
            .set(chatMessage)
            .addOnSuccessListener { documentReference ->
                //Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val toast = Toast.makeText(this, "Submitted", Toast.LENGTH_LONG)
                toast.show()
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                val toast = Toast.makeText(this, "Failed", Toast.LENGTH_LONG)
                toast.show()
            }
    }

    private fun getMessages (chatHistory: ChipGroup) {
        val db = Firebase.firestore

        var messagesInHistory = 0

        db.collection("messages")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val message = document.get("text").toString()
                    val user = document.get("user").toString()

                    addMessageToChat(message, user, chatHistory)

                    messagesInHistory++

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        chatMessages = messagesInHistory
    }


}