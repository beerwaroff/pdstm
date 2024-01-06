package ru.beerwaroff.pdstm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangingRequest {

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}",
            message = "Пароль не соответствует требованиям")
    @NotNull(message = "Обязательно поле.")
    private String password;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}",
            message = "Пароль не соответствует требованиям")
    @NotNull(message = "Обязательно поле.")
    private String duplicatePassword;
}
