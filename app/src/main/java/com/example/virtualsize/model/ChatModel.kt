package com.example.virtualsize.model

data class ChatModel(val id: String, val text: String, val fromId: String, val toId: String, val timestamp: Long,
                     val jam: String, val username: String, val foto: String) {
    constructor() : this("", "", "", "", -1, "", "", "")
}