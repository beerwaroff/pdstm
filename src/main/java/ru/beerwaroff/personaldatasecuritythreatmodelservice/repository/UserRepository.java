package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository;

import ru.beerwaroff.personaldatasecuritythreatmodelservice.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByCode(String code);
    void deleteByEmail(String email);
}
