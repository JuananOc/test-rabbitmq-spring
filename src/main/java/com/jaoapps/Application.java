package com.jaoapps;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static com.jaoapps.RabbitMQConfig.*;

@SpringBootApplication
public class Application {

    public static final boolean DORMIR = true;
    public static final boolean ANADIR = true;
    public static final long TIEMPO_DORMIR = 2000l;
    private static final int NUM_MENSAJES = 2;

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);


        // CLiente de Rabbit MQ -> Encargado de añadir cosas a la cola

        final RabbitTemplate rabbitTemplate = context.getBean(RabbitTemplate.class);

        if (ANADIR) {
            for (int i = 1; i <= NUM_MENSAJES; i++) {
                final String mensaje = i + "º mensaje";
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, BINDING_KEY, mensaje);
                System.out.println("Enviado el mensaje: " + mensaje);
            }
        }
    }
}