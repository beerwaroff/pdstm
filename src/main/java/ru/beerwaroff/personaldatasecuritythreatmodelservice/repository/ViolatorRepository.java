package ru.beerwaroff.personaldatasecuritythreatmodelservice.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.builder.QueryHelper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.FilterRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.Violator;

import java.util.List;

import static org.jooq.generated.pdstm.Tables.IMPLEMENTATION_GOALS;
import static org.jooq.generated.pdstm.Tables.VIOLATORS;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_CATEGORIES;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_IMPLEMENTATION_GOALS;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_LEVELS;
import static org.jooq.generated.pdstm.Tables.VIOLATOR_LEVELS_CATEGORIES;

@Repository
@RequiredArgsConstructor
public class ViolatorRepository {
    private final DSLContext jooq;

    public List<Violator> findAll(List<FilterRequest> filter) {
        return jooq.selectDistinct(
                        VIOLATORS.NAME,
                        VIOLATOR_LEVELS.NAME.as("level"),
                        VIOLATOR_CATEGORIES.NAME.as("category"),
                        VIOLATOR_LEVELS_CATEGORIES.ID.cast(String.class).as("lcId")
                ).from(VIOLATORS)
                        .leftJoin(VIOLATOR_LEVELS_CATEGORIES)
                        .on(VIOLATORS.VIOLATOR_LEVEL_CATEGORY_ID.eq(VIOLATOR_LEVELS_CATEGORIES.ID))
                        .leftJoin(VIOLATOR_LEVELS)
                        .on(VIOLATOR_LEVELS_CATEGORIES.LEVEL_ID.eq(VIOLATOR_LEVELS.ID))
                        .leftJoin(VIOLATOR_CATEGORIES)
                        .on(VIOLATOR_LEVELS_CATEGORIES.CATEGORY_ID.eq(VIOLATOR_CATEGORIES.ID))
                        .leftJoin(VIOLATOR_IMPLEMENTATION_GOALS)
                        .on(VIOLATORS.ID.eq(VIOLATOR_IMPLEMENTATION_GOALS.VIOLATOR_ID))
                        .leftJoin(IMPLEMENTATION_GOALS)
                        .on(VIOLATOR_IMPLEMENTATION_GOALS.IMPLEMENTATION_GOAL_ID.eq(IMPLEMENTATION_GOALS.ID))
                .where(QueryHelper.filter(filter))
        .fetchInto(Violator.class);
    }


    public List<String> findByLcId(List<String> lcId) {
        return jooq.selectDistinct(
                        VIOLATOR_CATEGORIES.NAME.concat(" ").concat(VIOLATOR_LEVELS.ID)
                ).from(VIOLATORS)
                .leftJoin(VIOLATOR_LEVELS_CATEGORIES)
                .on(VIOLATORS.VIOLATOR_LEVEL_CATEGORY_ID.eq(VIOLATOR_LEVELS_CATEGORIES.ID))
                .leftJoin(VIOLATOR_LEVELS)
                .on(VIOLATOR_LEVELS_CATEGORIES.LEVEL_ID.eq(VIOLATOR_LEVELS.ID))
                .leftJoin(VIOLATOR_CATEGORIES)
                .on(VIOLATOR_LEVELS_CATEGORIES.CATEGORY_ID.eq(VIOLATOR_CATEGORIES.ID))
                .where(VIOLATOR_LEVELS_CATEGORIES.ID.cast(String.class).in(lcId)
                ).fetchInto(String.class);
    }
}
