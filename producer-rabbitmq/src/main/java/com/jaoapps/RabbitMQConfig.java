package com.prueba;


import com.prueba.producer.Producer;
import com.prueba.producer.RabbitMQProducer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setChannelTransacted(true);  //When true we have

//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if (ack) {
//                System.out.println("El mensaje con id " + correlationData.getId() + "ha sido recibido.");
//            } else {
//                System.out.println("El mensaje con id " + correlationData.getId() + "ha fallado por la posible causa: " + cause);
//            }
//        });

        return rabbitTemplate;
    }

    @Bean
    public Producer producer(RabbitTemplate rabbitTemplate) {
        return new RabbitMQProducer(rabbitTemplate);
    }
}
