package ru.beerwaroff.personaldatasecuritythreatmodelservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.config.property.MailSenderProperty;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailSenderConfig {
    private final MailSenderProperty property;

    @Bean
    public JavaMailSender getJavaMailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost(property.getHost());
        mailSender.setPort(property.getPort());
        mailSender.setUsername(property.getUsername());
        mailSender.setPassword(property.getPassword());
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", property.getProtocol());
        return mailSender;
    }
}
