package ru.beerwaroff.pdstm.builder;

import lombok.NoArgsConstructor;
import org.jooq.Condition;
import org.springframework.lang.Nullable;
import ru.beerwaroff.pdstm.dto.request.FilterRequest;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.noCondition;
import static ru.beerwaroff.pdstm.builder.QueryHelper.Operator.EQ;

@NoArgsConstructor(access = PRIVATE)
public final class QueryHelper {

    public static Condition filter(@Nullable List<FilterRequest> filters) {
        var condition = noCondition();
        if (filters != null) {
            for (var filter :  filters) {
                var operator = filter.getOperator();
                if (EQ.name().equals(operator)) {
                    condition = condition.and(
                            eq(filter.getColumn(), filter.getValues())
                    );
                }
            }
        }
        return condition;
    }

    public static Condition filter(@Nullable FilterRequest filter) {
        var condition = noCondition();
        if (filter != null) {
                var operator = filter.getOperator();
                if (EQ.name().equals(operator)) {
                    condition = condition.and(
                            eq(filter.getColumn(), filter.getValues())
                    );
                }
            }
        return condition;
    }

    private static Condition eq(String column, List<String> values) {
        var condition = noCondition();
        for (var value : values) {
            condition = condition.or(
                    field(column).cast(String.class).eq(value)
            );
        }
        return condition;
    }

    enum Operator {
        EQ
    }
}
