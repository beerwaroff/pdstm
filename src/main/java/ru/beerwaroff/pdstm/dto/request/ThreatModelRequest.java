package ru.beerwaroff.pdstm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThreatModelRequest {
    private CommonInfo info;
    private FilterRequest negativeConsequencesFilter;
    private FilterRequest influenceObjectsFilter;
    private List<FilterRequest> violatorFilter;
}
