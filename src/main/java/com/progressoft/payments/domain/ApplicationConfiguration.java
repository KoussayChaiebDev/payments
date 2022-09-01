package com.progressoft.payments.domain;

import com.ibm.msg.client.jms.internal.*;
import com.progressoft.payments.domain.controller.*;
import com.progressoft.payments.domain.jms.*;
import com.progressoft.payments.domain.repository.PaymentRepository;
import com.progressoft.payments.domain.service.PaymentService;
import com.progressoft.payments.domain.validations.ValidatorImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.*;

@Configuration
@EnableJms
public class ApplicationConfiguration {

    @Bean
    public ValidatorImpl getValidatorBean(PaymentRepository paymentRepository) {
        return new ValidatorImpl(paymentRepository);
    }

    @Bean
    public PaymentService getPaymentServiceBean(ValidatorImpl validator, PaymentRepository paymentRepository) {
        return new PaymentService(validator, paymentRepository);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PaymentControllerApiDelegateImpl paymentDelegate(PaymentService paymentService) {
        return new PaymentControllerApiDelegateImpl(paymentService);
    }

    @Bean
    public JmsErrorMessages InwardErrors(@Value("${payments.error.inward.queue}") String stx)
        {
            return new JmsErrorMessages();
    }

    @Bean
    public InwardPaymentsListener inwardPaymentsListener(PaymentService paymentService){
        return new InwardPaymentsListener(paymentService);
    }
}
