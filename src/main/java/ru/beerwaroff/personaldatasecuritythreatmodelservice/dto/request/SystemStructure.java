package ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request;

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
public class SystemStructure {
    private String pc;
    private String software;
    private String securityTools;
}
