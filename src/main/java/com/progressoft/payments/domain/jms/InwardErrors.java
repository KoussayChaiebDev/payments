package com.progressoft.payments.domain.jms;

import org.springframework.jms.annotation.*;
import org.springframework.jms.core.*;

import javax.jms.*;


public class InwardErrors {
    private JmsTemplate jmsTemplate;

    public InwardErrors() {
    }

    @JmsListener(id = "InwardPaymentsErrorListener", destination = "${payments.error.inward.queue}")
    public void newErrorMessage(TextMessage textMessage) {
        try {
            textMessage.getText();
            textMessage.getJMSMessageID();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
