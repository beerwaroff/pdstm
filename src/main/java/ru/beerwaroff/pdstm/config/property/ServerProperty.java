package ru.beerwaroff.pdstm.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
@Setter
@Getter
public class ServerProperty {
    private String host;
    private int port;
}
