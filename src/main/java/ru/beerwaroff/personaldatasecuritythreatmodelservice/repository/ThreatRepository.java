package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ActualThreat;

import java.util.List;

import static org.jooq.generated.pdstm.Tables.IMPLEMENTATION_METHODS;
import static org.jooq.generated.pdstm.Tables.INFLUENCE_OBJECTS;
import static org.jooq.generated.pdstm.Tables.NEGATIVE_CONSEQUENCES;
import static org.jooq.generated.pdstm.Tables.OBJECTS_METHODS;
import static org.jooq.generated.pdstm.Tables.THREATS_NC;
import static org.jooq.generated.pdstm.Tables.THREATS_OBJECTS;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_LC_METHODS;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_LEVELS_CATEGORIES;
import static org.jooq.generated.pdstm.tables.Threats.THREATS;

@Repository
@RequiredArgsConstructor
public class ThreatRepository {
    private final DSLContext jooq;

    public List<ActualThreat> findAll() {
        return jooq.select(THREATS.ID, THREATS.NAME)
                .from(THREATS)
                .fetch(res -> ActualThreat.builder()
                        .id(res.value1())
                        .name(res.value2())
                        .violators(
                                jooq.select(VIOLATOR_LEVELS_CATEGORIES.ID.cast(String.class))
                                        .from(THREATS)
                                        .leftJoin(THREATS_OBJECTS)
                                        .on(THREATS.ID.eq(THREATS_OBJECTS.THREAT_ID))
                                        .leftJoin(INFLUENCE_OBJECTS)
                                        .on(THREATS_OBJECTS.OBJECT_ID.eq(INFLUENCE_OBJECTS.ID))
                                        .leftJoin(OBJECTS_METHODS)
                                        .on(INFLUENCE_OBJECTS.ID.eq(OBJECTS_METHODS.OBJECT_ID))
                                        .leftJoin(IMPLEMENTATION_METHODS)
                                        .on(OBJECTS_METHODS.METHOD_ID.eq(IMPLEMENTATION_METHODS.ID))
                                        .leftJoin(VIOLATOR_LC_METHODS)
                                        .on(IMPLEMENTATION_METHODS.ID.eq(VIOLATOR_LC_METHODS.IMPL_METHOD_ID))
                                        .leftJoin(VIOLATOR_LEVELS_CATEGORIES)
                                        .on(VIOLATOR_LC_METHODS.VIOLATOR_LC_ID.eq(VIOLATOR_LEVELS_CATEGORIES.ID))
                                        .where(THREATS.ID.eq(res.value1()))
                                        .fetchInto(String.class)
                        ).objects(
                                jooq.select(INFLUENCE_OBJECTS.NAME)
                                        .from(THREATS)
                                        .leftJoin(THREATS_OBJECTS)
                                        .on(THREATS.ID.eq(THREATS_OBJECTS.THREAT_ID))
                                        .leftJoin(INFLUENCE_OBJECTS)
                                        .on(THREATS_OBJECTS.OBJECT_ID.eq(INFLUENCE_OBJECTS.ID))
                                        .where(THREATS.ID.eq(res.value1()))
                                        .fetchInto(String.class)
                        ).negativeConsequences(
                                jooq.select(NEGATIVE_CONSEQUENCES.ID)
                                        .from(THREATS)
                                        .leftJoin(THREATS_NC)
                                        .on(THREATS.ID.eq(THREATS_NC.THREAT_ID))
                                        .leftJoin(NEGATIVE_CONSEQUENCES)
                                        .on(THREATS_NC.NC_ID.eq(NEGATIVE_CONSEQUENCES.ID))
                                        .where(THREATS.ID.eq(res.value1()))
                                        .fetchInto(String.class)
                        ).methods(
                                jooq.select(IMPLEMENTATION_METHODS.ID)
                                        .from(THREATS)
                                        .leftJoin(THREATS_OBJECTS)
                                        .on(THREATS.ID.eq(THREATS_OBJECTS.THREAT_ID))
                                        .leftJoin(INFLUENCE_OBJECTS)
                                        .on(THREATS_OBJECTS.OBJECT_ID.eq(INFLUENCE_OBJECTS.ID))
                                        .leftJoin(OBJECTS_METHODS)
                                        .on(INFLUENCE_OBJECTS.ID.eq(OBJECTS_METHODS.OBJECT_ID))
                                        .leftJoin(IMPLEMENTATION_METHODS)
                                        .on(OBJECTS_METHODS.METHOD_ID.eq(IMPLEMENTATION_METHODS.ID))
                                        .where(THREATS.ID.eq(res.value1()))
                                        .fetchInto(String.class)
                        )
                        .build()
                );
    }
}
