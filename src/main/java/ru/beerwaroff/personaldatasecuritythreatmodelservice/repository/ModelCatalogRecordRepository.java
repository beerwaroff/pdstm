package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ModelCatalogRecord;

import java.util.List;

import static org.jooq.generated.pdstm.Tables.IMPACT_TYPES;
import static org.jooq.generated.pdstm.Tables.IMPLEMENTATION_METHODS;
import static org.jooq.generated.pdstm.Tables.NEGATIVE_CONSEQUENCES;
import static org.jooq.generated.pdstm.Tables.RISK_TYPES;
import static org.jooq.generated.pdstm.Tables.THREATS;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_LEVELS;

@Repository
@RequiredArgsConstructor
public class ModelCatalogRecordRepository {
    private final DSLContext jooq;

    public List<ModelCatalogRecord> findRiskTypes() {
        return jooq.selectFrom(RISK_TYPES)
                .fetchInto(ModelCatalogRecord.class);
    }

    public List<ModelCatalogRecord> findNegativeConsequences() {
        return jooq.selectFrom(NEGATIVE_CONSEQUENCES)
                .fetchInto(ModelCatalogRecord.class);
    }

    public List<ModelCatalogRecord> findImpactTypes() {
        return jooq.selectFrom(IMPACT_TYPES)
                .fetchInto(ModelCatalogRecord.class);
    }

    public List<ModelCatalogRecord> findViolatorTypes() {
        return jooq.selectFrom(VIOLATOR_LEVELS)
                .fetchInto(ModelCatalogRecord.class);
    }

    public List<ModelCatalogRecord> findImplementationMethods() {
        return jooq.selectFrom(IMPLEMENTATION_METHODS)
                .fetchInto(ModelCatalogRecord.class);
    }

    public List<ModelCatalogRecord> findThreats() {
        return jooq.selectFrom(THREATS)
                .fetchInto(ModelCatalogRecord.class);
    }
}
