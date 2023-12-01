package ru.beerwaroff.personaldatasecuritythreatmodelservice.builder;

import org.jooq.Condition;
import org.jooq.SelectForUpdateStep;
import org.jooq.SelectWhereStep;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.FilterRequest;

import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.noCondition;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.builder.QueryHelper.Operator.EQ;

@Component
public class QueryHelper {
    public SelectForUpdateStep<?> build(SelectWhereStep<?> select, List<FilterRequest> filters) {
        return select
                .where(this.filter(filters));
    }

    public SelectForUpdateStep<?> build(SelectWhereStep<?> select, FilterRequest filter) {
        return select
                .where(this.filter(filter));
    }

    private Condition filter(@Nullable List<FilterRequest> filters) {
        var condition = noCondition();
        if (filters != null) {
            for (var filter :  filters) {
                var operator = filter.getOperator();
                if (EQ.name().equals(operator)) {
                    condition.and(
                            eq(filter.getColumn(), filter.getValues())
                    );
                }
            }
        }
        return condition;
    }

    private Condition filter(@Nullable FilterRequest filter) {
        var condition = noCondition();
        if (filter != null) {
                var operator = filter.getOperator();
                if (EQ.name().equals(operator)) {
                    condition.and(
                            eq(filter.getColumn(), filter.getValues())
                    );
                }
            }
        return condition;
    }

    private Condition eq(String column, List<String> values) {
        var condition = noCondition();
        for (var value : values) {
            condition.and(
                    field(column).eq(value)
            );
        }
        return condition;
    }

    enum Operator {
        EQ
    }
}
