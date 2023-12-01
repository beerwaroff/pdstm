package ru.beerwaroff.personaldatasecuritythreatmodelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

@SpringBootApplication(exclude= R2dbcAutoConfiguration.class)
public class PersonalDataSecurityThreatModelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalDataSecurityThreatModelServiceApplication.class, args);
	}
}
