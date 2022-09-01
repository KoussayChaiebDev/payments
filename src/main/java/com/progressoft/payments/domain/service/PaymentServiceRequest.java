package com.progressoft.payments.domain.service;

import com.progressoft.payments.domain.entities.Entity;
import com.progressoft.payments.domain.repository.TransactionSpecifications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

import static com.progressoft.payments.domain.entities.QueryConstants.*;

public class PaymentServiceRequest {
    private final Specification<Entity> specs;
    private final Pageable pageable;

    public PaymentServiceRequest(Map<String, String> filters, Pageable pageable) {
        specs = new TransactionSpecifications().likeTransactionReference(filters.get(TX_REF))
                .dateBetween(filters.get(MIN_AMOUNT), filters.get(MAX_AMOUNT))
                .dateEquals(filters.get(DATE))
                .debtorAccountLike(filters.get(DEB_ACC))
                .creditorAccountLike(filters.get(CRED_ACC))
                .amountEquals(filters.get(AMOUNT))
                .amountBetween(filters.get(MIN_AMOUNT), filters.get(MAX_AMOUNT))
                .build();
        this.pageable = pageable;
    }

    public Specification<Entity> getSpecs() {
        return specs;
    }

    public Pageable getPageable() {
        return pageable;
    }
}
