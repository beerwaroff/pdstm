package ru.beerwaroff.personaldatasecuritythreatmodelservice.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.AuthenticationService;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final AuthenticationService service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        authentication = service.authenticate(
                authentication.getName().trim(),
                authentication.getCredentials().toString().trim()
        );

        if (authentication != null) {
            return authentication;
        } else {
            throw new AuthenticationCredentialsNotFoundException("Неверно введена почта или пароль.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
