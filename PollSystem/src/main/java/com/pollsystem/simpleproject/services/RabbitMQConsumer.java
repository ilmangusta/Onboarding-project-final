package com.pollsystem.simpleproject.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pollsystem.simpleproject.domain.PollWinnerMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    @RabbitListener(queues = "poll.winner.mail")
    public void receiveMessage(String message) {
        System.out.println("Messaggio ricevuto dalla coda: " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PollWinnerMessage converted_message = objectMapper.readValue(message, PollWinnerMessage.class);
            System.out.println("Mando risultato poll all'owner: "+ converted_message.getOwnerEmail());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
