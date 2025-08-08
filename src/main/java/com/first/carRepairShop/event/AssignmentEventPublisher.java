package com.first.carRepairShop.event;

import com.first.carRepairShop.dto.websocket.AssignmentCanceledMessage;
import com.first.carRepairShop.dto.websocket.AssignmentNewMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignmentEventPublisher {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpUserRegistry simpUserRegistry;


    public void sendNewAssignment(AssignmentNewMessage message) {
        String username = message.getUsername();
        System.out.println("📋 Active WebSocket Users:");
        simpUserRegistry.getUsers().forEach(simpUser ->
                System.out.println("🧑 User in registry: " + simpUser.getName())
        );
        SimpUser user = simpUserRegistry.getUser(username);
        if (user == null) {
            System.err.println("❌ No WebSocket session found for user: " + username);
            return;
        }
        if (username == null) {
            System.err.println("❌ Username is null. Cannot send WebSocket message.");
            return;
        }
        simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/assignments", message);
        System.out.println("📤 Sending message to: " + username);
    }

    public void sendCanceledAssignment(AssignmentCanceledMessage message) {
        String username = message.getUsername();
        SimpUser user = simpUserRegistry.getUser(username);
        if (user == null) {
            System.err.println("❌ No WebSocket session found for user: " + username);
            return;
        }
        if (username == null) {
            System.err.println("❌ Username is null. Cannot send WebSocket message.");
            return;
        }
        simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/assignments", message);
        System.out.println("📤 Sending message to: " + username);

    }
}
