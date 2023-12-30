package ru.beerwaroff.personaldatasecuritythreatmodelservice.mapper;

import lombok.NonNull;
import lombok.val;
import org.mapstruct.Mapper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.CommonInfo;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.FilterRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.QuizAnswers;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.ThreatModelRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.ThreatModelRequestProxy;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

@Mapper(componentModel = "spring")
public interface ThreatModelRequestMapper {

    default ThreatModelRequest map(ThreatModelRequestProxy proxy) {
        val EQ_OPERATOR = "EQ";
        val answers = proxy.getAnswers();
        List<String> negativeConsequencesFilterValues = getNegativeConsequencesFilterValues(answers);
        List<String> influenceObjectsFilterValues = getInfluenceObjectFilterValues(answers);
        List<String> violatorFilterValues = getViolatorFilterValues(answers);
        List<String> implementationGoalsFilterValues = getImplementationGoalsFilterValues(answers);
        return ThreatModelRequest.builder()
                .info(
                        CommonInfo.builder()
                                .names(proxy.getNames())
                                .responsible(proxy.getResponsible())
                                .structure(proxy.getStructure())
                                .info(proxy.getInfo())
                                .build()
                ).negativeConsequencesFilter(
                        FilterRequest.builder()
                                .column("negative_consequences.id")
                                .operator(EQ_OPERATOR)
                                .values(negativeConsequencesFilterValues)
                                .build()
                ).influenceObjectsFilter(
                        FilterRequest.builder()
                                .column("influence_objects.id")
                                .operator(EQ_OPERATOR)
                                .values(influenceObjectsFilterValues)
                                .build()
                ).violatorFilter(
                        List.of(
                                FilterRequest.builder()
                                        .column("violators.id")
                                        .operator(EQ_OPERATOR)
                                        .values(violatorFilterValues)
                                        .build(),
                                FilterRequest.builder()
                                        .column("implementation_goals.id")
                                        .operator(EQ_OPERATOR)
                                        .values(implementationGoalsFilterValues)
                                        .build()
                                )
                ).build();
    }

    @NonNull
    private static List<String> getNegativeConsequencesFilterValues(QuizAnswers answers) {
        List<String> negativeConsequencesFilterValues = new ArrayList<>();
        negativeConsequencesFilterValues.add("НП.1");
        negativeConsequencesFilterValues.add("НП.2");
        negativeConsequencesFilterValues.add("НП.3");
        negativeConsequencesFilterValues.add("НП.4");
        if (answers.getIsGovernmental() != null) {
            negativeConsequencesFilterValues.add("НП.5");
        }
        return negativeConsequencesFilterValues;
    }

    @NonNull
    private static List<String> getInfluenceObjectFilterValues(QuizAnswers answers) {
        List<String> influenceObjectsFilterValues = new ArrayList<>();
        influenceObjectsFilterValues.add("1");
        influenceObjectsFilterValues.add("2");
        influenceObjectsFilterValues.add("3");
        influenceObjectsFilterValues.add("4");
        influenceObjectsFilterValues.add("5");
        if (answers.getIsAccessRemovable() != null) {
            influenceObjectsFilterValues.add("6");
        }
        if (answers.getIsProtect() != null) {
            influenceObjectsFilterValues.add("7");
        }
        return influenceObjectsFilterValues;
    }

    @NonNull
    private static List<String> getViolatorFilterValues(QuizAnswers answers) {
        List<String> violatorFilterValues = new ArrayList<>();
        violatorFilterValues.add("11");
        violatorFilterValues.add("12");
        violatorFilterValues.add("4");
        if (answers.getIsGovernmental() != null) {
            violatorFilterValues.add("1");
            violatorFilterValues.add("2");
        }
        if (answers.getIsExternal() != null) {
            violatorFilterValues.add("3");
            violatorFilterValues.add("13");

        }
        if (answers.getHasDevelopers() != null) {
            violatorFilterValues.add("6");
        }
        if (answers.getHasOpponents() != null) {
            violatorFilterValues.add("5");
        }
        if (answers.getIsAccessKz() != null) {
            violatorFilterValues.add("7");
            violatorFilterValues.add("8");
            violatorFilterValues.add("9");
            violatorFilterValues.add("10");
        }
        return violatorFilterValues;
    }

    @NonNull
    private static List<String> getImplementationGoalsFilterValues(QuizAnswers answers) {
        List<String> implementationGoalsFilterValues = new ArrayList<>();
        implementationGoalsFilterValues.add("4");
        implementationGoalsFilterValues.add("6");
        implementationGoalsFilterValues.add("7");
        if (answers.getIsGovernmental() != null) {
            implementationGoalsFilterValues.add("1");
            implementationGoalsFilterValues.add("2");
        }
        if (answers.getHasOpponents() != null) {
            implementationGoalsFilterValues.add("5");
        }
        if (answers.getIsFinancial() != null) {
            implementationGoalsFilterValues.add("3");
        }
        return implementationGoalsFilterValues;
    }
}
