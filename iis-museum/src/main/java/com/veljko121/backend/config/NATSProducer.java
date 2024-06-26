package com.veljko121.backend.config;

import org.springframework.stereotype.Service;

import io.nats.client.Connection;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NATSProducer {
    
    private final Connection natsConnection;

    public void sendMessage(String subject, String message) {
        try {
            natsConnection.publish(subject, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
