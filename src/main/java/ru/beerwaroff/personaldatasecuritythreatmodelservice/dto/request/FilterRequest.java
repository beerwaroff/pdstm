package ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
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
