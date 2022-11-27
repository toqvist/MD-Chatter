package com.toqvist.chatter

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


open class ChatActivity : AppCompatActivity() {


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

            sendMessage(message, sender, chatHistory)
            clearChatHistory(chatHistory)
            getMessages(chatHistory)

            messageForm.text = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        val messageForm = findViewById<EditText>(R.id.messageForm)
        outState.putString("messageInput", messageForm.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val messageForm = findViewById<EditText>(R.id.messageForm)
        val message = savedInstanceState.getString("messageInput")
        messageForm.text = message as Editable

    }

    fun addMessageToChat(message: String, sender: String, chatHistory: ChipGroup) {
        val newChip = Chip(this)
        newChip.setText(sender + " : " + message)
        chatHistory?.addView(newChip)
    }

    private fun clearChatHistory(chatHistory: ChipGroup) {
        chatHistory.removeAllViews()
    }


    private fun sendMessage(message: String, sender: String, chatHistory: ChipGroup) {

        var messages = 0

        for (message in chatHistory.children) {
            messages++
        }
        val chatMessage = hashMapOf(
            "text" to message,
            "user" to sender,
        )

        val db = Firebase.firestore

        db.collection("messages").document(messages.toString())
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

                    messagesInHistory = messagesInHistory+1

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        //chatMessages = messagesInHistory
    }


}