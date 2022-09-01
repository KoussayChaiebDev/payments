package com.progressoft.payments.domain.controller;

import com.progressoft.payments.domain.entities.*;
import com.progressoft.payments.domain.jms.*;
import com.progressoft.payments.domain.openapi.model.*;
import com.progressoft.payments.domain.openapi.payments.*;
import com.progressoft.payments.domain.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.jms.*;
import org.springframework.jms.annotation.*;
import org.springframework.jms.core.*;
import org.springframework.web.bind.annotation.*;


import javax.jms.*;
import java.math.BigDecimal;
import java.text.*;
import java.time.LocalDate;
import java.util.*;

import static com.progressoft.payments.domain.entities.QueryConstants.*;
import static java.util.Optional.ofNullable;

public class PaymentControllerApiDelegateImpl implements PaymentControllerApiDelegate {

    @Autowired
    private JmsTemplate jmsTemplate;
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
    private final PaymentService paymentService;

    public PaymentControllerApiDelegateImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<List<String>> importPayments(List<Payment> payment) {
        try {
            PaymentServiceResponse importServiceResponse = paymentService.receivePayments(payment);
            if (importServiceResponse.getErrors() == null || !importServiceResponse.getErrors().isEmpty()) {
                jmsTemplate.convertAndSend("${payments.error.inward.queue}", importServiceResponse.getErrors());
                return ResponseEntity.badRequest().body(importServiceResponse.getErrors());
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

    }

    @Override
    public ResponseEntity findPayments(Pageable pageable, String transactionReference, BigDecimal amount, BigDecimal minAmount, BigDecimal maxAmount, String debtorAccount, String creditorAccount, LocalDate minDate, LocalDate date, LocalDate maxDate) {
        Map<String, String> filters = new HashMap<>();
        ofNullable(transactionReference).ifPresent(val -> filters.put(TX_REF, val));
        ofNullable(amount).ifPresent(val -> filters.put(AMOUNT, String.valueOf(val)));
        ofNullable(minAmount).ifPresent(val -> filters.put(MIN_AMOUNT, String.valueOf(val)));
        ofNullable(maxAmount).ifPresent(val -> filters.put(MAX_AMOUNT, String.valueOf(val)));
        ofNullable(debtorAccount).ifPresent(val -> filters.put(DEB_ACC, val));
        ofNullable(creditorAccount).ifPresent(val -> filters.put(CRED_ACC, val));
        ofNullable(date).ifPresent(val -> filters.put(DATE, String.valueOf(val)));
        ofNullable(maxDate).ifPresent(val -> filters.put(MAX_DATE, String.valueOf(val)));
        ofNullable(minDate).ifPresent(val -> filters.put(MIN_DATE, String.valueOf(val)));

        PaymentServiceResponse paymentServiceResponse = paymentService.findPayments(new PaymentServiceRequest(filters, pageable));


        return paymentServiceResponse.getResultAsPage() != null
                ? ResponseEntity.ok(paymentServiceResponse.getResultAsPage())
                : ResponseEntity.ok(paymentServiceResponse.getResultAsList());
    }

}

