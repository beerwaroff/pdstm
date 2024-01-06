package ru.beerwaroff.pdstm.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.jooq.DSLContext;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Repository;
import ru.beerwaroff.pdstm.dto.ImplementationMethod;
import ru.beerwaroff.pdstm.dto.ModelCatalogRecord;

import java.util.List;

import static org.jooq.generated.pdstm.tables.ImplementationMethods.IMPLEMENTATION_METHODS;
import static org.jooq.generated.pdstm.tables.InfluenceObjects.INFLUENCE_OBJECTS;
import static org.jooq.generated.pdstm.tables.ObjectsMethods.OBJECTS_METHODS;
import static org.jooq.generated.pdstm.tables.ViolatorLcMethods.VIOLATOR_LC_METHODS;
import static org.jooq.generated.pdstm.tables.ViolatorLevelsCategories.VIOLATOR_LEVELS_CATEGORIES;
import static org.jooq.generated.pdstm.tables.Violators.VIOLATORS;

@Repository
@RequiredArgsConstructor
public class ImplementationMethodRepository {
    private final DSLContext jooq;

    public List<ImplementationMethod> findAll() {
        val violators = jooq.selectDistinct(VIOLATORS.ID, VIOLATORS.NAME)
                .from(VIOLATORS)
                .fetchInto(ModelCatalogRecord.class);

        return jooq.selectDistinct(
                VIOLATORS.NAME,
                INFLUENCE_OBJECTS.NAME,
                VIOLATORS.ID,
                INFLUENCE_OBJECTS.ID
        ).from(IMPLEMENTATION_METHODS)
                .leftJoin(VIOLATOR_LC_METHODS)
                .on(IMPLEMENTATION_METHODS.ID.eq(VIOLATOR_LC_METHODS.IMPL_METHOD_ID))
                .leftJoin(VIOLATOR_LEVELS_CATEGORIES)
                .on(VIOLATOR_LC_METHODS.VIOLATOR_LC_ID.eq(VIOLATOR_LEVELS_CATEGORIES.ID))
                .leftJoin(VIOLATORS)
                .on(VIOLATOR_LEVELS_CATEGORIES.ID.eq(VIOLATORS.VIOLATOR_LEVEL_CATEGORY_ID))
                .leftJoin(OBJECTS_METHODS)
                .on(IMPLEMENTATION_METHODS.ID.eq(OBJECTS_METHODS.METHOD_ID))
                .leftJoin(INFLUENCE_OBJECTS)
                .on(OBJECTS_METHODS.OBJECT_ID.eq(INFLUENCE_OBJECTS.ID))
                .fetch(res -> ImplementationMethod.builder()
                        .violator(res.value1())
                        .object(res.value2())
                        .methods(
                                this.getJoins(
                                        jooq.selectDistinct(IMPLEMENTATION_METHODS.ID)
                                                .from(IMPLEMENTATION_METHODS)
                                        ).where(VIOLATORS.ID.eq(res.value3()))
                                        .and(INFLUENCE_OBJECTS.ID.eq(res.value4()))
                                        .fetchInto(String.class)
                        ).build()
                );
    }

    private SelectOnConditionStep<?> getJoins(SelectJoinStep<?> selectJoinStep) {
        return selectJoinStep
                .leftJoin(VIOLATOR_LC_METHODS)
                .on(IMPLEMENTATION_METHODS.ID.eq(VIOLATOR_LC_METHODS.IMPL_METHOD_ID))
                .leftJoin(VIOLATOR_LEVELS_CATEGORIES)
                .on(VIOLATOR_LC_METHODS.VIOLATOR_LC_ID.eq(VIOLATOR_LEVELS_CATEGORIES.ID))
                .leftJoin(VIOLATORS)
                .on(VIOLATOR_LEVELS_CATEGORIES.ID.eq(VIOLATORS.VIOLATOR_LEVEL_CATEGORY_ID))
                .leftJoin(OBJECTS_METHODS)
                .on(IMPLEMENTATION_METHODS.ID.eq(OBJECTS_METHODS.METHOD_ID))
                .leftJoin(INFLUENCE_OBJECTS)
                .on(OBJECTS_METHODS.OBJECT_ID.eq(INFLUENCE_OBJECTS.ID));
    }
}
