package com.progressoft.payments.domain.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "payment")
public class Entity {
    @Id
    @Column(name = "transactionReference")
    private String transactionReference;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "senderBic")
    private String senderBic;
    @Column(name = "valueDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate valueDate;
    @Column(name = "receiverBic")
    private String receiverBic;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "debtor_account")
    private Customer debtor;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "creditor_account")
    private Customer creditor;

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSenderBic() {
        return senderBic;
    }

    public void setSenderBic(String senderBic) {
        this.senderBic = senderBic;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public String getReceiverBic() {
        return receiverBic;
    }

    public void setReceiverBic(String receiverBic) {
        this.receiverBic = receiverBic;
    }

    public Customer getDebtor() {
        return debtor;
    }

    public void setDebtor(Customer debtor) {
        this.debtor = debtor;
    }

    public Customer getCreditor() {
        return creditor;
    }

    public void setCreditor(Customer creditor) {
        this.creditor = creditor;
    }

    @Override
    public String toString() {
        return "{" +
                "transactionReference='" + transactionReference + '\'' +
                ", amount=" + amount +
                ", senderBic='" + senderBic + '\'' +
                ", valueDate='" + valueDate + '\'' +
                ", receiverBic='" + receiverBic + '\'' +
                ", debtor=" + debtor +
                ", creditor=" + creditor +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != Entity.class) return false;
        return ((Entity) obj).getTransactionReference().equals(this.transactionReference);
    }

    @Override
    public int hashCode() {
        return this.getTransactionReference().hashCode();
    }
}
