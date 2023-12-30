package ru.beerwaroff.personaldatasecuritythreatmodelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private String code;

    @Builder.Default
    private boolean isActive = false;
}