package com.prueba;

import com.prueba.consumer.RabbitMQEmailConsumer;
import com.prueba.consumer.RabbitMQStatsConsumer;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;


@Configuration
public class RabbitMQConfig {

    private static final String EMAIL_QUEUE_NAME = "email_queue";
    private static final String STATS_QUEUE_NAME = "stats_queue";


    @Bean
    PlatformTransactionManager platformTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
        return new RabbitTransactionManager(cachingConnectionFactory);
    }

    @Bean
    SimpleMessageListenerContainer emailContainer(ConnectionFactory connectionFactory, RabbitMQEmailConsumer emailConsumer, PlatformTransactionManager platformTransactionManager) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(EMAIL_QUEUE_NAME);
        container.setMessageListener(emailListenerAdapter(emailConsumer));

        container.setAutoStartup(true); // container should start when ApplicationContext does
        container.setAutoDeclare(false);    //  the container will NOT redeclare AMQP queues, exchanges and bindings

        container.setChannelTransacted(true);   //signal that all messages (send and receive) should be acknowledged in a transaction
        container.setTransactionManager(platformTransactionManager);    // transaction manager given by spring amqp for rabbitmq

        container.setAcknowledgeMode(AcknowledgeMode.AUTO); //the container acknowledge the message automatically unless MessageListener throws an exception
        container.setDefaultRequeueRejected(true);  // when true messages that are rejected because listener threw an exception should be requeued

        return container;
    }

    @Bean
    RabbitMQEmailConsumer emailConsumer() {
        return new RabbitMQEmailConsumer();
    }

    @Bean
    MessageListenerAdapter emailListenerAdapter(RabbitMQEmailConsumer emailConsumer) {
        return new MessageListenerAdapter(emailConsumer, "consume");
    }

    @Bean
    SimpleMessageListenerContainer statsContainer(ConnectionFactory connectionFactory, RabbitMQStatsConsumer statsConsumer, PlatformTransactionManager platformTransactionManager) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(STATS_QUEUE_NAME);
        container.setMessageListener(statsListenerAdapter(statsConsumer));

        container.setAutoStartup(true); // container should start when ApplicationContext does
        container.setAutoDeclare(false);    //  the container will NOT redeclare AMQP queues, exchanges and bindings

        container.setChannelTransacted(true);   //signal that all messages (send and receive) should be acknowledged in a transaction
        container.setTransactionManager(platformTransactionManager);    // transaction manager given by spring amqp for rabbitmq

        container.setAcknowledgeMode(AcknowledgeMode.AUTO); //the container acknowledge the message automatically unless MessageListener throws an exception
        container.setDefaultRequeueRejected(true);  // when true messages that are rejected because listener threw an exception should be requeued
        return container;
    }

    @Bean
    RabbitMQStatsConsumer statsConsumer() {
        return new RabbitMQStatsConsumer();
    }

    @Bean
    MessageListenerAdapter statsListenerAdapter(RabbitMQStatsConsumer statsConsumer) {
        return new MessageListenerAdapter(statsConsumer, "consume");
    }
}