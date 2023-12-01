package ru.beerwaroff.personaldatasecuritythreatmodelservice.exception;

public class PasswordNotMatchException extends PasswordException {
    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}
