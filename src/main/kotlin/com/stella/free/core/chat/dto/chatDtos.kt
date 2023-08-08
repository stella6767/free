package com.stella.free.core.chat.dto

import java.time.LocalDateTime


//data class ChatRoom(
//    val id:String,
//    val name:String,
//    val userCount:Int,
//){
//}

data class ChatSendDto(
    val type: MessageType,
    val sender: String,
    val message:String = "",
    val time:String = LocalDateTime.now().toString()
){
    enum class MessageType {
        JOIN,
        TALK,
        LEAVE;
    }

}


data class ChatResDto(
    val sender: String,
    val html:String,
)