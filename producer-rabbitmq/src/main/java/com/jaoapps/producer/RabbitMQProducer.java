package com.prueba.producer;

import com.prueba.message.Email;
import com.prueba.message.Stats;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;


public class RabbitMQProducer implements Producer {

    private static String TEAMS_EXCHANGE = "teams_exchange";

    private static final String EMAIL_QUEUE_NAME = "email_queue";
    private static final String STATS_QUEUE_NAME = "stats_queue";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private int i = 0;

    @Transactional
    @Override
    public void send(Object message) {
        if (message instanceof Email) {
            System.out.println("CANAL TRANSACCIONAL: " + rabbitTemplate.isChannelTransacted());
            rabbitTemplate.convertAndSend(TEAMS_EXCHANGE, EMAIL_QUEUE_NAME, createPersistentMessage(message), new CorrelationData(((Email) message).getName()));
            System.out.println("[RabbitMQProducer (" + i++ + ") ] Email enviado!");

        } else if (message instanceof Stats) {
            rabbitTemplate.convertAndSend(TEAMS_EXCHANGE, STATS_QUEUE_NAME, createPersistentMessage(message), new CorrelationData("stats"));
            System.out.println("[RabbitMQProducer (" + i++ + "] Stats enviadas!");
        }
    }

    private Message createPersistentMessage(Object message) {
        final MessageProperties properties = new MessageProperties();
        properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        final byte[] body = SerializationUtils.serialize(message);
        return MessageBuilder.withBody(body).andProperties(properties).build();
    }
}