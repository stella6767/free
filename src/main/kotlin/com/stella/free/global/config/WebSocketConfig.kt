package com.stella.free.global.config

import com.stella.free.global.util.logger
import jakarta.websocket.*
import jakarta.websocket.server.ServerEndpoint
import org.springframework.context.annotation.Configuration


@Configuration
@ServerEndpoint("/ws")
class WebSocketConfig(

) {

    private val log = logger()


    /**
     * 웹소켓 세션을 담는 ArrayList
     */
    private val sessionList = ArrayList<Session>()


    /**
     * 웹소켓 사용자 연결 성립하는 경우 호출
     */
    @OnOpen
    fun handleOpen(session: Session?) {

        if (session != null) {
            val sessionId: String = session.getId()
            log.info("client is connected. sessionId == [$sessionId]")
            sessionList.add(session)
            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
            sendMessageToAll("***** [USER-$sessionId] is connected. *****")
        }

    }


    /**
     * 웹소켓 메시지(From Client) 수신하는 경우 호출
     */
    @OnMessage
    fun handleMessage(message: String, session: Session?): String? {
        if (session != null) {
            val sessionId: String = session.getId()
            println("message is arrived. sessionId == [$sessionId] / message == [$message]")
            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
            sendMessageToAll("[USER-$sessionId] $message")
        }
        return null
    }


    /**
     * 웹소켓 사용자 연결 해제하는 경우 호출
     */
    @OnClose
    fun handleClose(session: Session?) {
        if (session != null) {
            val sessionId: String = session.getId()
            println("client is disconnected. sessionId == [$sessionId]")

            // 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
            sendMessageToAll("***** [USER-$sessionId] is disconnected. *****")
        }
    }


    /**
     * 웹소켓 에러 발생하는 경우 호출
     */
    @OnError
    fun handleError(t: Throwable) {
        t.printStackTrace()
    }


    /**
     * 웹소켓 연결 성립되어 있는 모든 사용자에게 메시지 전송
     */
    fun sendMessageToAll(message: String?): Boolean {
        if (sessionList == null) {
            return false
        }
        val sessionCount = sessionList.size
        if (sessionCount < 1) {
            return false
        }
        var singleSession: Session? = null
        for (i in 0 until sessionCount) {
            singleSession = sessionList[i]
            if (singleSession == null) {
                continue
            }
            if (!singleSession.isOpen()) {
                continue
            }
            sessionList[i].getAsyncRemote().sendText(message)
        }
        return true
    }

}