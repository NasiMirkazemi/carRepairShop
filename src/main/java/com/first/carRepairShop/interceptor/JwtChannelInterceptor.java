package com.first.carRepairShop.interceptor;

import com.first.carRepairShop.exception.TokenValidationException;
import com.first.carRepairShop.security.JwtUtil;
import com.first.carRepairShop.security.UserAppServiceDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public  class JwtChannelInterceptor implements ChannelInterceptor {
    //You are implementing ChannelInterceptor, which allows you to intercept every WebSocket message before it is processed.

    private final JwtUtil jwtUtil;
    private final UserAppServiceDetail userAppServiceDetail;

        /*
     What does this method do?
     It runs before Spring handles the incoming WebSocket message.
     It lets you:
        •	Read the message.
        •	Check or validate the message.
        •	Modify the message if you want.
        •	Reject the message (by throwing an exception or returning null).
        •	Attach extra information (for example, attach the authenticated Principal like you are doing!).
    */

    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //This is the actual WebSocket message (contains headers and payload).

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //You create a helper object accessor that lets you easily read the STOMP headers (like Authorization, destination, etc.)

            /*You check if the incoming message is a CONNECT message.
             CONNECT happens when the client first opens the WebSocket connection.
             ⚡ Important: You only want to check the token during the connection, not every message.*/
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            //You try to read the Authorization header that the client sent in the CONNECT frame.
            String authHeader = accessor.getFirstNativeHeader("Authorization");

            //Make sure it starts with the word â€œBearer â€œ, which is the normal way JWT tokens are sent.
            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                //You extract the real token by removing "Bearer " prefix.
                String token = authHeader.substring(7);
                System.out.println("🟢 JWT Token in CONNECT frame: " + token);
                try {
                    jwtUtil.isTokenValid(token);


                    //If the token is valid, you extract the username from inside the token payload.
                    String username = jwtUtil.extractUsername(token);
                    System.out.println("✅ WebSocket CONNECT from user: " + username);


                    //You use your UserAppServiceDetail (your custom UserDetailsService) to load full user details (roles, password, etc.)
                    UserDetails userDetails = userAppServiceDetail.loadUserByUsername(username);
                    System.out.println("✅ UserDetail Username: " + userDetails.getUsername());

                        /*•	You create an Authentication object.
                          •	This object:
                          •	Holds userDetails
                          •	Has no password (null)
                          •	Has user’s roles (userDetails.getAuthorities())
                          This object will act as the Principal (identity) for the WebSocket session.*/
                    //authentication is a type of Principal (Authentication implements Principal).
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    System.out.println("➡️ Principal class: " + authentication.getClass());
                    System.out.println("✅ Principal set for session: " + authentication.getName());


                          /*•You attach this Authentication to the accessor.
                            •Now, Spring knows who is connected through the WebSocket.
                            •It links this WebSocket session to your user!*/
                    accessor.setUser((authentication));
                } catch (TokenValidationException e) {
                    try {
                        throw new AccessDeniedException("Invalid token: " + e.getMessage());
                    } catch (AccessDeniedException ex) {
                        throw new RuntimeException(ex);
                    }

                }

            }
        }
        return message;
    }
}