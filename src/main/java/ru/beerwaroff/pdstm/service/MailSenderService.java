package ru.beerwaroff.pdstm.service;

public interface MailSenderService {
    void send(String to, String subject, String text);
}
