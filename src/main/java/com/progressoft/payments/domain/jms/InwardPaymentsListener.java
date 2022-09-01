package com.progressoft.payments.domain.jms;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.progressoft.payments.domain.openapi.model.*;
import com.progressoft.payments.domain.service.*;
import org.springframework.jms.annotation.*;

import javax.jms.*;
import java.text.*;
import java.util.*;

public class InwardPaymentsListener {

    private final PaymentService paymentService;

    public InwardPaymentsListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @JmsListener(id = "InwardPaymentsListener", destination = "${payments.inward.queue}", concurrency = "${payments.listener-concurrency}")
    public void newMessage(TextMessage textMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        Map<String, Payment> map;
        try {
            String input = textMessage.getText();
            textMessage.getJMSMessageID();
            List<Payment> payments = objectMapper.readValue(input, new TypeReference<List<Payment>>() {
            });
            PaymentServiceResponse importServiceResponse = paymentService.receivePayments(payments);

        } catch (JMSException | JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
