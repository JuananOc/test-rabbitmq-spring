package com.prueba.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class Action {
    private static final Logger log = LoggerFactory.getLogger(Action.class);

    @Transactional
    public void execute() {
        if (log.isTraceEnabled()) {
            log.trace("Ini action {}, transaction = {}", getClass().getSimpleName(), TransactionSynchronizationManager.isActualTransactionActive());
        }

        try {
            executeInAction();

        } catch (Exception e) {

            log.error("Error executing action. Action = " + toString(), e);
            throw e;

        } finally {
            if (log.isTraceEnabled()) {
                log.trace("End action {}", getClass().getSimpleName());
            }
        }
    }

    protected abstract void executeInAction();
}
