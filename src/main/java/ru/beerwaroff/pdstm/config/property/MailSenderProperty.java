package ru.beerwaroff.pdstm.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
@Setter
@Getter
public class MailSenderProperty {
    private String host;
    private String username;
    private String password;
    private int port;
    private String protocol;
}
