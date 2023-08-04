package com.stella.free.core.chat.dto




//data class ChatRoom(
//    val id:String,
//    val name:String,
//    val userCount:Int,
//){
//
//}



data class ChatDto(
    val type: MessageType,
    val sender: String,
    val message:String,
    val time:String
){
    enum class MessageType {
        ENTER, TALK, LEAVE;
    }

}