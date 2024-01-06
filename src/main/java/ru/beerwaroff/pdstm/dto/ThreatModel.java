package ru.beerwaroff.pdstm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.beerwaroff.pdstm.dto.request.CommonInfo;

import java.util.List;

@Builder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThreatModel {
    private CommonInfo info;
    private ModelCatalog catalog;
    private List<NegativeConsequences> negativeConsequences;
    private List<InfluenceObject> influenceObjects;
    private List<Violator> violators;
    private List<ImplementationMethod> methods;
    private List<ActualThreat> actualThreats;

    //META
    private String link;
    private String username;
}
