package com.progressoft.payments.domain.validations;

import com.progressoft.payments.domain.openapi.model.*;
import com.progressoft.payments.domain.repository.PaymentRepository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class ValidatorImpl implements Validator {

    private final PaymentRepository paymentRepository;

    public ValidatorImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }
    @Override
    public List<String> validate(List<com.progressoft.payments.domain.openapi.model.Payment> payments) throws ParseException {
        List<String> errors = new ArrayList<>();
        List<String> processedPaymentIds = new ArrayList<>();
        for(com.progressoft.payments.domain.openapi.model.Payment payment : payments){
            List<Optional<String>> validators = Arrays.asList(
                    validateAmount(payment),
                    validateDebtorAddress(payment),
                    validateTransactionReference(payment, processedPaymentIds),
                    validateCreditorAddress(payment),
                    validateCreditorAccount(payment),
                    validateDebtorAccount(payment),
                    validateCreditorName(payment),
                    validateDate(payment),
                    validateReceiverBic(payment),
                    validateSenderBic(payment),
                    validateDebtorName(payment)
            );
            processedPaymentIds.add(payment.getTransactionReference());
            errors.addAll(validators.stream().filter(Optional::isPresent).map(Optional::get).toList());
        }
        return errors;
    }


    private Optional<String> validateDate(com.progressoft.payments.domain.openapi.model.Payment payment) {
        if(payment.getValueDate() == null)
            return Optional.of("Date is not present");
        if(!(payment.getValueDate().compareTo(LocalDate.now())==0))
            return Optional.of("Date must be today");
        return Optional.empty();
    }
    private Optional<String> validateTransactionReference(com.progressoft.payments.domain.openapi.model.Payment entity, List<String> processedIds){
      if(entity.getTransactionReference() == null)
          return Optional.of("Transaction reference is not present");
      if(entity.getTransactionReference().length() != 16)
          return Optional.of("Transaction reference must be 16 characters");
      if(processedIds.contains(entity.getTransactionReference())||paymentRepository.existsById(entity.getTransactionReference()))
          return Optional.of("Transaction reference must be unique");
      return Optional.empty();
    }

    private Optional<String> validateAmount(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getAmount() ==  null)
            return Optional.of("amount is not present");
        if(entity.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            return Optional.of("amount must be greater than 0");
        if(entity.getAmount().compareTo(BigDecimal.valueOf(100000)) >= 0)
            return Optional.of("amount is more than 100000");
        return Optional.empty();
    }
    private Optional<String> validateSenderBic(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getSenderBic() == null)
            return Optional.of("Sender Bic is not present.");
        if(entity.getSenderBic().length() != 8)
            return Optional.of("Sender Bic must be 8 characters.");
        if(entity.getSenderBic().equals(entity.getReceiverBic()))
            return Optional.of("Sender and Receiver Bic are identical.");
        return Optional.empty();
    }
    private Optional<String> validateReceiverBic(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getReceiverBic() == null)
            return Optional.of("Sender Bic is not present.");
        if(entity.getReceiverBic().length() != 8)
            return Optional.of("Sender Bic must be 8 characters.");
        return Optional.empty();
    }

    private Optional<String> validateDebtorAccount(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getDebtor().getAccount() == null)
            return Optional.of("Debtor account is not present");
        if(entity.getDebtor().getAccount().length() != 5)
            return Optional.of("Debtor account must be 5 characters long");
        return Optional.empty();
    }

    private Optional<String> validateCreditorAccount(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getCreditor().getAccount() == null)
            return Optional.of("Creditor account is not present");
        if(entity.getCreditor().getAccount().length() != 5)
            return Optional.of("Creditor account must be 5 characters long");
        return Optional.empty();
    }
    private Optional<String> validateCreditorName(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getCreditor().getName() == null)
            return Optional.empty();
        if(entity.getCreditor().getName().length() > 35)
            return Optional.of("Creditor name must not exceed 35 characters");
        return Optional.empty();
    }

    private Optional<String> validateDebtorName(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getDebtor().getName() == null)
            return Optional.empty();
        if(entity.getDebtor().getName().length() > 35)
            return Optional.of("Debtor name must not exceed 35 characters");
        return Optional.empty();
    }

    private Optional<String> validateDebtorAddress(com.progressoft.payments.domain.openapi.model.Payment entity){
        if(entity.getDebtor().getAddress1() == null && entity.getDebtor().getAddress2() == null)
            return Optional.empty();
        if(entity.getDebtor().getAddress1()!= null && entity.getDebtor().getAddress1().length() > 35)
            return Optional.of("Debtor address must not exceed 35 characters");
        if(entity.getDebtor().getAddress2()!= null && entity.getDebtor().getAddress2().length() > 35)
            return Optional.of("Debtor address must not exceed 35 characters");
        return Optional.empty();
    }

    private Optional<String> validateCreditorAddress(Payment entity){
        if(entity.getCreditor().getAddress1() == null && entity.getCreditor().getAddress2() == null)
            return Optional.empty();
        if(entity.getCreditor().getAddress1()!= null && entity.getCreditor().getAddress1().length() > 35)
            return Optional.of("Creditor address must not exceed 35 characters");
        if(entity.getCreditor().getAddress2()!= null && entity.getCreditor().getAddress2().length() > 35)
            return Optional.of("Creditor address must not exceed 35 characters");
        return Optional.empty();
    }


}
