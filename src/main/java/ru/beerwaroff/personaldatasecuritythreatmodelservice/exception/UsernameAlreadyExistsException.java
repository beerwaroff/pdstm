package ru.beerwaroff.personaldatasecuritythreatmodelservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
