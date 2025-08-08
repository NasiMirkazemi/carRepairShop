package com.first.carRepairShop.WebSocket;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/*
@Component
@RequiredArgsConstructor
public class MechanicSocketHandler extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(MechanicSocketHandler.class);
    private final Map<String,WebSocketSession> sessions= new ConcurrentHashMap<>();//ConcurrentHashMap is a thread-safe version of HashMap.

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(),session);
        logger.info("Connected :{}", session.getId());
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus){
        logger.info("Mechanic WebSocket closed: "+session.getId());

    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) {
        String payLoad = message.getPayload();
        logger.info("Message  received: {} ", payLoad);
        if (payLoad.startsWith("accepted:")) {
            String[] parts = payLoad.split(":");
            String assignmentId=parts[1];
            logger.info("Mechanic accepted Assignment:{}",assignmentId);
        }
    }

}
*/
