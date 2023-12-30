package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.builder.QueryHelper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.FilterRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.NegativeConsequences;

import java.util.List;

import static org.jooq.generated.pdstm.Tables.NEGATIVE_CONSEQUENCES;
import static org.jooq.generated.pdstm.Tables.RISK_TYPES;

@Repository
@RequiredArgsConstructor
public class NegativeConsequencesRepository {
    private final DSLContext jooq;

    public List<NegativeConsequences> find(FilterRequest filter) {
        return jooq.select(
                        NEGATIVE_CONSEQUENCES.ID,
                        NEGATIVE_CONSEQUENCES.NAME,
                        RISK_TYPES.NAME.as("risk")
                ).from(NEGATIVE_CONSEQUENCES)
                .leftJoin(RISK_TYPES)
                .on(NEGATIVE_CONSEQUENCES.RISK_ID.eq(RISK_TYPES.ID))
                .where(QueryHelper.filter(filter))
                .fetchInto(NegativeConsequences.class);
    }
}
