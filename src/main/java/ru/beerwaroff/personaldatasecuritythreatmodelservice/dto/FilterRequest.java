package ru.beerwaroff.personaldatasecuritythreatmodelservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {

    @NotNull(message = "Обязательно поле.")
    private String column;

    @NotNull(message = "Обязательно поле.")
    private String operator;

    @NotNull(message = "Обязательно поле.")
    private List<String> values;
}
