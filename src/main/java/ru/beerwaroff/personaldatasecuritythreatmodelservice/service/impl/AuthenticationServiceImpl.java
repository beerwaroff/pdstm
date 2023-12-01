package ru.beerwaroff.personaldatasecuritythreatmodelservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.AuthenticationService;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.UserService;

import static java.util.Collections.singletonList;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;

    @Override
    public Authentication authenticate(String email, String password) {
        val user = userService.getByEmail(email);
        if (!user.isActive()) {
            throw new AuthenticationServiceException("Подтвердите аккаунт.");
        }

        if (user.getPassword().equals(sha256Hex(password))) {
            return new UsernamePasswordAuthenticationToken(
                    email, password, singletonList(
                            new SimpleGrantedAuthority(USER.name())
                    )
            );
        } else {
            throw new AuthenticationServiceException("Аутентификация провалена.");
        }
    }
}
