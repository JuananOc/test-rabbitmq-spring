package com.prueba.action.sender;



import com.prueba.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SendActionFactory {
    @Autowired
    private Producer producer;

    @Bean
    @Scope("prototype")
    public SendAction sendAction(Object message){
        return new SendAction(producer, message);
    }
}
