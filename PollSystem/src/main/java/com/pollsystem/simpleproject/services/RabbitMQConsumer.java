package com.pollsystem.simpleproject.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "poll.winner.mail")
    public void receiveMessage(String message) {
        System.out.println("Messaggio ricevuto dalla coda: " + message);
        // Logica per inviare email all'owner del poll
    }
}
