package com.progressoft.payments.domain.repository;

import com.progressoft.payments.domain.entities.Customer_;
import com.progressoft.payments.domain.entities.Entity;
import com.progressoft.payments.domain.entities.Entity_;
import org.springframework.data.jpa.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionSpecifications {

    //Update final result
    private Specification<Entity> latestAddedSpecification = (root, query, cb) -> cb.like(root.get(Entity_.transactionReference), "%" + "" + "%");
    // add filter after filter
    private TransactionSpecifications addSpecification(Specification<Entity> specification) {
        latestAddedSpecification = latestAddedSpecification.and(specification);
        return this;
    }
    //Final result
    public Specification<Entity> build() {
        return latestAddedSpecification;
    }
    public TransactionSpecifications likeTransactionReference(String transactionReference) {
         if (transactionReference ==null)
             return this;
        return addSpecification((root, query, cb) -> cb.like(root.get(Entity_.transactionReference), "%" + transactionReference + "%"));
    }

    public TransactionSpecifications debtorAccountLike(String debtorAccount) {
         if(debtorAccount == null)
                return this;
         return addSpecification((root, query, cb) -> cb.like(root.get(Entity_.debtor).get(Customer_.ACCOUNT), "%" + debtorAccount + "%"));
    }

    public TransactionSpecifications creditorAccountLike(String creditorAccount) {
         if (creditorAccount == null)
                return this;
        return  addSpecification((root, query, cb) -> cb.like(root.get(Entity_.creditor).get(Customer_.ACCOUNT), "%" + creditorAccount + "%"));
    }

    public TransactionSpecifications amountEquals(String amountString) {
        if (amountString == null)
            return this;
        BigDecimal amount = new BigDecimal(amountString);
        return addSpecification((root, query, cb) -> cb.equal(root.get(Entity_.amount), amount));
    }

    public TransactionSpecifications amountBetween(String maxAmountString, String minAmountString) {
        if (minAmountString == null || maxAmountString == null)
            return this;
        BigDecimal minAmount = new BigDecimal(minAmountString);
        BigDecimal maxAmount = new BigDecimal(maxAmountString);
        return addSpecification((root, query, cb) -> cb.between(root.get(Entity_.amount), minAmount, maxAmount));
    }

    public TransactionSpecifications dateEquals(String dateString) {
        if (dateString == null)
            return this;
        LocalDate date = LocalDate.parse(dateString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return addSpecification((root, query, cb) -> cb.equal(root.get(Entity_.valueDate), date));
    }

    public TransactionSpecifications dateBetween(String minDateString, String maxDateString) {
        if (minDateString == null || maxDateString == null)
            return this;

        LocalDate maxDate = LocalDate.parse(maxDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate minDate = LocalDate.parse(minDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return addSpecification((root, query, cb) -> cb.between(root.get(Entity_.valueDate), minDate, maxDate));
    }




}
