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
public class ModelCatalog {
    private List<ModelCatalogRecord> riskTypes;
    private List<ModelCatalogRecord> negativeConsequences;
    private List<ModelCatalogRecord> impactTypes;
    private List<ModelCatalogRecord> violatorTypes;
    private List<ModelCatalogRecord> implementationMethods;
    private List<ModelCatalogRecord> threats;
}
