package com.IshaVishakhaPramod.IdempotentPaymentApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IdempotentPaymentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdempotentPaymentApiApplication.class, args);
	}

}
