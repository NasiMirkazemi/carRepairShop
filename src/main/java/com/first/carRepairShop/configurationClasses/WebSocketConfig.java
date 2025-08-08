package com.first.carRepairShop.configurationClasses;

import com.first.carRepairShop.interceptor.JwtChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtChannelInterceptor jwtChannelInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {//override from WebSocketMessageBrokerConfigurer
        registry.addEndpoint("/ws/core/mechanic")
                .setAllowedOrigins("*");// <-- your WebSocket handshake endpoint
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {//override from WebSocketMessageBrokerConfigurer
        config.enableSimpleBroker("/topic","/user");// for server → client
        config.setApplicationDestinationPrefixes("/app"); // for client → server
        config.setUserDestinationPrefix("/user"); // for sending private messages
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor); // 👈 this is our custom interceptor
    }

}
