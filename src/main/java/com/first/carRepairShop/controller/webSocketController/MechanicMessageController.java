package com.first.carRepairShop.controller.webSocketController;

import com.first.carRepairShop.WebSocket.BroadcastMessage;
import com.first.carRepairShop.WebSocket.service.WebSocketMessagingService;
import com.first.carRepairShop.dto.websocket.AssignmentDecisionMessage;
import com.first.carRepairShop.services.impl.AssignmentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MechanicMessageController {
    private final Logger log = LoggerFactory.getLogger(MechanicMessageController.class);

    private final WebSocketMessagingService messagingService;
    private final AssignmentServiceImpl assignmentServiceImpl;

    @MessageMapping("/send-message") // Client sends to /app/send-message
    public String handleSendMessage(@Payload BroadcastMessage message) {
        log.info("Received message from client: " + message);
        return "Server received: " + message;
    }

    /*If you use /queue/** → it sends only to one user (private)
    /user/ = special path for private messages
    queue/private = the “name” where the user is listening (it’s like a mailbox name) */

    @MessageMapping("/assignment/decision")
    public void handleAssignmentDecision(@Payload AssignmentDecisionMessage decisionMessage) {
        assignmentServiceImpl.processAssignmentDecision(decisionMessage);
    }

}

