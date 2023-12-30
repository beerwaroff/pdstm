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
public class Violator {
    private String name;
    private String level;
    private String category;
    private String lcId;
}
