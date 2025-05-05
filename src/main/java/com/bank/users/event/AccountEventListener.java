package com.bank.users.event;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventListener {

    @RabbitListener(queues = "account.created.queue")
    public void handle(AccountCreatedEvent event) {
        System.out.println("Received account creation event: " + event);
        // Aquí puedes validar si clientId existe o realizar otra acción
    }
}
