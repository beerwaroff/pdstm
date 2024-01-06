package ru.beerwaroff.pdstm.service;

import ru.beerwaroff.pdstm.dto.User;

public interface UserService {
    void add(User user);
    User getByEmail(String email);
    void changePassword(String code, String password, String duplicatePassword);
    void deleteByEmail(String email);
    void sendCode(String email, String code);
}
