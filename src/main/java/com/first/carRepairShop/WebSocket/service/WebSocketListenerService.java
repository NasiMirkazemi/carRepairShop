package com.first.carRepairShop.WebSocket.service;

import com.first.carRepairShop.WebSocket.BroadcastMessage;
import com.first.carRepairShop.dto.websocket.AssignmentCanceledMessage;
import com.first.carRepairShop.entity.Assignment;
import com.first.carRepairShop.event.AssignmentCanceledEvent;
import com.first.carRepairShop.event.AssignmentCreatedEvent;
import com.first.carRepairShop.services.AssignmentNotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
public class WebSocketListenerService {
    private final SimpMessagingTemplate messagingTemplate;
    private final AssignmentNotificationService notificationService;
    Logger logger = LoggerFactory.getLogger(WebSocketListenerService.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent connectedEvent) {
        logger.info("New WebSocket connection detected!");

    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent subscribeEvent) {
        logger.info("New subscription detected!");
        BroadcastMessage welcomeMessage = new BroadcastMessage("🚗 Welcome Mechanics!");
        messagingTemplate.convertAndSend("/topic/messages", welcomeMessage);

    }

    //Run this method only after the surrounding database transaction successfully commits.
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAssignmentCreated(AssignmentCreatedEvent event) {
        System.out.println("on assignment");
        Assignment assignment = event.getAssignment();
        notificationService.notifyMechanicNewAssignment(assignment);
    }

    @EventListener
    public void OnAssignmentCanceled(AssignmentCanceledEvent event) {
        System.out.println("om Canceled assignment");
        notificationService.notifyMechanicCanceledAssignment(event);
    }

}
