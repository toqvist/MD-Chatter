package com.toqvist.chatter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class ChatActivity : AppCompatActivity() {

    var chatHistory: ChipGroup? = null
    var chatMessages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatHistory = findViewById<ChipGroup>(R.id.chatHistory)

        addMessage("Hello")
        addMessage("How are you?")
        addMessage("Hello")
        addMessage("How are you?")
        addMessage("Hello")
        addMessage("How are you?")

    }


    fun addMessage(message: String) {
        chatMessages++

        val newChip = Chip(this)
        newChip.setText(message)

        chatHistory?.addView(newChip)
    }
}