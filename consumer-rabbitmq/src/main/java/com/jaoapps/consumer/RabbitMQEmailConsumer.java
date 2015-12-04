package com.prueba.consumer;

import com.prueba.message.Email;
import org.springframework.amqp.utils.SerializationUtils;

public class RabbitMQEmailConsumer implements Consumer {

    @Override
    public void consume(Object message) {
        final Object deserializedMessage = SerializationUtils.deserialize((byte[]) message);

        if (deserializedMessage instanceof Email) {
            consumeEmail((Email) deserializedMessage);
        } else {
            // TODO throw exception
            System.out.println("Nada :(");
        }
    }

    private void consumeEmail(Email message) {
        if (message.getName().equals("email")) {
            System.out.println("Cambio del nombre del mensaje.");
            message.setName("nuevo");

            System.out.println("Mensaje cambiado");
            System.out.println("Aquí peta el mensaje");
            throw new JavierException();

        } else if (message.getName().equals("nuevo")) {
            System.out.println("El mensaje no ha cambiado su estado");
        }

    }
}
