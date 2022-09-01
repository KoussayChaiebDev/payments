package com.progressoft.payments.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.*;

import java.util.Properties;

@SpringBootApplication
public class PaymentsApplication {
	public static void main(String[] args) {SpringApplication.run(PaymentsApplication.class, args);}

}
