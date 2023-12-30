package ru.beerwaroff.personaldatasecuritythreatmodelservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.config.property.ServerProperty;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.exception.PasswordNotMatchException;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.exception.UsernameAlreadyExistsException;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.User;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.UserRepository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.MailSenderService;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.UserService;

import static java.util.UUID.randomUUID;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.ControllerConstant.USERS_URL;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final String USERNAME_NOT_FOUND_EXCEPTION_MESSAGE = "Пользователь не найден.";
    private final UserRepository repository;
    private final ServerProperty property;
    private final MailSenderService mailSender;

    @Transactional
    @Override
    public void add(User user) {
        val code = generateCode();
        try {
            repository.save(user);
            this.sendCode(user.getEmail(), code);
        } catch (Exception e) {
            throw new UsernameAlreadyExistsException("Пользователь уже существует");
        }
    }

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE));
    }

    @Override
    public void changePassword(String code, String password, String duplicatePassword) {
        if (!password.equals(duplicatePassword)) {
            throw new PasswordNotMatchException("Пароли не совпадают");
        }
       val user =  repository.findByCode(code)
               .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND_EXCEPTION_MESSAGE));
        user.setPassword(sha256Hex(password));
        user.setActive(true);
        repository.save(user);
    }

    @Override
    public void deleteByEmail(String email) {
        if (getContext().getAuthentication().getName().equals(email)) {
            repository.deleteByEmail(email);
        }
    }

    @Transactional
    @Override
    public void sendCode(String email, String code) {
        val subject = "Активация аккаунта.";
        val message = String.format(
                "Установите пароль по следующей ссылке:\n" +
                        property.getHost() + property.getPort() + USERS_URL + "/activation/%s", code
        );
        val user = this.getByEmail(email);
        user.setCode(code);
        repository.save(user);
        mailSender.send(email, subject, message);
    }

    private String generateCode() {
        return randomUUID().toString();
    }

    private User getByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден."));
    }

}
