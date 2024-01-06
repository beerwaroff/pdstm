package ru.beerwaroff.pdstm.dto;

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
public class ActualThreat {
    private String id;
    private String name;
    private List<String> violators;
    private List<String> objects;
    private List<String> negativeConsequences;
    private List<String> methods;
}
