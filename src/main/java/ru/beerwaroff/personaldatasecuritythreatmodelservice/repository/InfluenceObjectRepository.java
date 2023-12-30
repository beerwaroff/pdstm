package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.builder.QueryHelper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.FilterRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.InfluenceObject;

import java.util.List;

import static org.jooq.generated.pdstm.Tables.IMPACT_TYPES;
import static org.jooq.generated.pdstm.Tables.INFLUENCE_OBJECTS;
import static org.jooq.generated.pdstm.Tables.INFLUENCE_OBJECTS_IMPACT_TYPES;

@Repository
@RequiredArgsConstructor
public class InfluenceObjectRepository {
    private final DSLContext jooq;

    public List<InfluenceObject> findAll(FilterRequest filter) {
        return jooq.selectDistinct(
                        INFLUENCE_OBJECTS.NAME,
                        INFLUENCE_OBJECTS.ID
                ).from(INFLUENCE_OBJECTS)
                .where(QueryHelper.filter(filter))
                .fetch(result -> InfluenceObject.builder()
                        .name(
                                result.getValue(INFLUENCE_OBJECTS.NAME)
                        ).impactTypeIds(
                                this.getImpactTypesByInfluenceObjectId(
                                        result.getValue(INFLUENCE_OBJECTS.ID)
                                )
                ).build()
        );
    }

    private List<String> getImpactTypesByInfluenceObjectId(int id) {
        return jooq.select(IMPACT_TYPES.ID)
                .from(IMPACT_TYPES)
                .leftJoin(INFLUENCE_OBJECTS_IMPACT_TYPES)
                .on(IMPACT_TYPES.ID.eq(INFLUENCE_OBJECTS_IMPACT_TYPES.IMPACT_TYPE_ID))
                .leftJoin(INFLUENCE_OBJECTS)
                .on(INFLUENCE_OBJECTS_IMPACT_TYPES.INFLUENCE_OBJECT_ID.eq(INFLUENCE_OBJECTS.ID))
                .where(INFLUENCE_OBJECTS.ID.eq(id))
                .fetchInto(String.class);
    }
}
