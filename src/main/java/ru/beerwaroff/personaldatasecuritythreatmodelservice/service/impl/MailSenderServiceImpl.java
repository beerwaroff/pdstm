package ru.beerwaroff.personaldatasecuritythreatmodelservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.config.property.MailSenderProperty;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.MailSenderService;

@Service
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender sender;
    private final MailSenderProperty property;

    @Override
    public void send(String to, String subject, String text) {
        val message = new SimpleMailMessage();
        message.setFrom(property.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }
}
