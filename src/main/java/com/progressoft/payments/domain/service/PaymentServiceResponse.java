package com.progressoft.payments.domain.service;

import com.progressoft.payments.domain.entities.Entity;
import org.springframework.data.domain.Page;
import org.springframework.jms.core.*;

import java.util.List;

public class PaymentServiceResponse {

    private Page<Entity> resultAsPage;
    private List<String> errors;
    private List<Entity> resultAsList;

    public PaymentServiceResponse(List<String> errors, int e){
        this.errors = errors;
    }
    public PaymentServiceResponse(List<Entity> resultAsList){
        this.resultAsList = resultAsList;
    }

    public PaymentServiceResponse(Page<Entity> resultAsPage){
        this.resultAsPage = resultAsPage;
        this.resultAsList = resultAsPage.getContent();
    }


    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return isOk()? "ok" : "";
    }

    public boolean isOk(){
        return errors == null|| errors.isEmpty();
    }

    public List<Entity> getResultAsList() {
        return resultAsList;
    }

    public Page<Entity> getResultAsPage() {
        return resultAsPage;
    }
}
