package ru.beerwaroff.personaldatasecuritythreatmodelservice.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    Authentication authenticate(String email, String password);
}
