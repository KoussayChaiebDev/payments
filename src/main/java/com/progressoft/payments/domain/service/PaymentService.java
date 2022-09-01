package com.progressoft.payments.domain.service;

import com.progressoft.payments.domain.entities.Customer;
import com.progressoft.payments.domain.entities.Entity;
import com.progressoft.payments.domain.jms.*;
import com.progressoft.payments.domain.openapi.model.*;
import com.progressoft.payments.domain.repository.PaymentRepository;
import com.progressoft.payments.domain.validations.ValidatorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.*;

import javax.jms.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

    public class PaymentService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ModelMapper modelMapper;

    private final ValidatorImpl validator;
    private final PaymentRepository paymentRepository;

    public PaymentService(ValidatorImpl validator, PaymentRepository paymentRepository){
        this.validator = validator;
        this.paymentRepository = paymentRepository;

    }

    public PaymentServiceResponse findPayments(PaymentServiceRequest paymentServiceRequest){
        return new PaymentServiceResponse(paymentRepository.findAll(paymentServiceRequest.getSpecs(), paymentServiceRequest.getPageable()));
    }

    public PaymentServiceResponse receivePayments(List<com.progressoft.payments.domain.openapi.model.Payment> payments) throws ParseException {
        PaymentServiceResponse paymentServiceResponse = new PaymentServiceResponse(validator.validate(payments), 1);
        List<Entity> entities = new ArrayList<>();
        if(paymentServiceResponse.getErrors().isEmpty()) {
            for(Payment payment : payments){
                Entity entity = new Entity();
                entity.setTransactionReference(payment.getTransactionReference());
                entity.setAmount(payment.getAmount());
                entity.setReceiverBic(payment.getReceiverBic());
                entity.setSenderBic(payment.getSenderBic());
                entity.setValueDate(payment.getValueDate());
                Customer newDebt= modelMapper.map(payment.getDebtor(), Customer.class );
                Customer newCred=modelMapper.map(payment.getCreditor(), Customer.class );
                entity.setDebtor(newDebt);
                entity.setCreditor(newCred);
                entities.add(entity);
            }
            paymentRepository.saveAll(entities);
        }
        return paymentServiceResponse;
    }
}
