package com.prueba;

import com.prueba.action.sender.SendActionFactory;
import com.prueba.message.Email;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        final SendActionFactory senderActionFactory = context.getBean(SendActionFactory.class);

        long before = System.currentTimeMillis();

        for (int i = 0; i < 1; i++) {
            senderActionFactory.sendAction(new Email("email")).execute();
        }

        System.out.println(" Milliseconds: " + (System.currentTimeMillis() - before));
    }
}