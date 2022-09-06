package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

// https://spring.io/guides/gs/messaging-stomp-websocket/

@SpringBootApplication
@Controller
@Configuration
@EnableWebSocketMessageBroker
public class DemoApplication implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple memory-based message broker to carry the greeting messages
        // back to the client on destinations prefixed with /topic
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // The client will attempt to connect to /my-websocket-service
        registry.addEndpoint("/my-websocket-service").withSockJS();
    }

    public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


    // If message is sent to /hello destination, greeting() method is called
    // Return value is broadcast to all subscribers of /topic/greetings
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        // Let's simulate a delay, the client can do whatever at this time.
        Thread.sleep(1000);


        return new Greeting("Hello, " + message.getName());
    }
}
