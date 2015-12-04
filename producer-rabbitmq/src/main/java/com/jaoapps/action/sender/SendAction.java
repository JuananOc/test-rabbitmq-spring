package com.prueba.action.sender;


import com.prueba.action.Action;
import com.prueba.producer.Producer;

public class SendAction extends Action {

    private final Producer producer;
    private final Object message;

    public SendAction(Producer producer, Object message) {
        this.producer = producer;
        this.message = message;
    }

    @Override
    protected void executeInAction() {
        producer.send(message);
    }
}
