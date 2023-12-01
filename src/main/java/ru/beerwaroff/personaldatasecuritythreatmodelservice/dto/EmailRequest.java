package ru.beerwaroff.personaldatasecuritythreatmodelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    @Email(message = "Почта не валидна.")
    @NotNull(message = "Обязательно поле.")
    private String email;
}
