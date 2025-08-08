package com.first.carRepairShop.WebSocket.service;

import com.first.carRepairShop.WebSocket.BroadcastMessage;
import com.first.carRepairShop.services.AssignmentNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketMessagingService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AssignmentNotificationService notificationService;


    public void sendPrivateMessage(String username,BroadcastMessage message){
        simpMessagingTemplate.convertAndSendToUser(username
                ,"/queue/private"
                ,"Private replay to "+username+": "+message);
    }


}
