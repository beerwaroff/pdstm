package ru.beerwaroff.personaldatasecuritythreatmodelservice.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordException extends AuthenticationException {
    public PasswordException(String msg) {
        super(msg);
    }
}
