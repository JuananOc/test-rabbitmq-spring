package com.prueba.consumer;

import com.prueba.message.Stats;
import org.springframework.amqp.utils.SerializationUtils;

public class RabbitMQStatsConsumer implements Consumer {

    @Override
    public void consume(Object message) {
        final Object deserializedMessage = SerializationUtils.deserialize((byte[]) message);

        if (deserializedMessage instanceof Stats) {
            consumeStats((Stats) deserializedMessage);
        } else {
            // TODO throw exception
            System.out.println("Nada :(");
        }
    }

    private void consumeStats(Stats message) {
        System.out.println("Mensaje '" + message.getName() + "' procesado.\n");
    }

}
