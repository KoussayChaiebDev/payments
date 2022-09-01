package com.progressoft.payments.domain.validations;

import com.progressoft.payments.domain.openapi.model.*;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
@Component
public interface Validator {
    List<String> validate(List<Payment> entities) throws ParseException ;

}
