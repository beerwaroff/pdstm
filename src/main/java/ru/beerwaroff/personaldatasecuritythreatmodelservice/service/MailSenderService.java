package ru.beerwaroff.personaldatasecuritythreatmodelservice.service;

public interface MailSenderService {
    void send(String to, String subject, String text);
}
