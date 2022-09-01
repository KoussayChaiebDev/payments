package com.progressoft.payments.domain.clientSide;

import com.progressoft.payments.domain.clientSide.Client;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {
    private String transactionReference;
    private BigDecimal amount;
    private String senderBic;
    private LocalDate valueDate;
    private String receiverBic;
    private Client debtor;
    private Client creditor;

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

    public Client getDebtor() {
        return debtor;
    }

    public void setDebtor(Client debtor) {
        this.debtor = debtor;
    }

    public Client getCreditor() {
        return creditor;
    }

    public void setCreditor(Client creditor) {
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
        if(obj.getClass() != Payment.class) return false;
        return ((Payment) obj).getTransactionReference().equals(this.transactionReference);
    }

    @Override
    public int hashCode() {
        return this.getTransactionReference().hashCode();
    }
}
