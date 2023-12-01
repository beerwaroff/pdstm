package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.impl;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.model.User;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.UserRepository;

import java.util.Optional;

import static org.jooq.generated.pdstm.Tables.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext jooq;

    @Override
    public void save(User user) {
        jooq.insertInto(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .onDuplicateKeyUpdate()
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.CODE, user.getCode())
                .set(USERS.IS_ACTIVE, user.isActive())
                .execute();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jooq.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptionalInto(User.class);
    }

    @Override
    public Optional<User> findByCode(String code) {
        return jooq.selectFrom(USERS)
                .where(USERS.CODE.eq(code))
                .fetchOptionalInto(User.class);
    }

    @Override
    public void deleteByEmail(String email) {
        jooq.delete(USERS)
                .where(USERS.EMAIL.eq(email))
                .execute();
    }
}
