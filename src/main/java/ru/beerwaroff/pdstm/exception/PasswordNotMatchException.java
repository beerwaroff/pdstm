package ru.beerwaroff.pdstm.exception;

public class PasswordNotMatchException extends PasswordException {
    public PasswordNotMatchException(String msg) {
        super(msg);
    }
}
