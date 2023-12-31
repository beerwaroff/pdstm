package ru.beerwaroff.personaldatasecuritythreatmodelservice.processor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ImplementationMethod;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.InfluenceObject;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.NegativeConsequences;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ThreatModel;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.Violator;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.ThreatRepository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.ViolatorRepository;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class ActualThreatDeterminationProcessor {
    private final ThreatRepository threatRepository;
    private final ViolatorRepository violatorRepository;
    private final ReportingProcessor nextProcessor;

    public ThreatModel determine(ThreatModel model) {
        val negativeConsequences = model.getNegativeConsequences().stream()
                .map(NegativeConsequences::getId)
                .collect(toList());
        val objects = model.getInfluenceObjects().stream()
                .map(InfluenceObject::getName)
                .collect(toList());
        val methods = model.getMethods().stream()
                .map(ImplementationMethod::getMethods)
                .flatMap(Collection::stream)
                .collect(toList());
        val violatorsLc = model.getViolators().stream()
                .map(Violator::getLcId)
                .collect(toList());
        model = model.toBuilder()
                .actualThreats(
                        threatRepository.findAll().stream()
                                .filter(
                                        threat -> threat.getObjects().stream()
                                                .anyMatch(
                                                        objects::contains
                                                ) && threat.getNegativeConsequences().stream()
                                                .anyMatch(
                                                        negativeConsequences::contains
                                                ) && threat.getMethods().stream()
                                                .anyMatch(
                                                        methods::contains
                                                ) && threat.getViolators().stream()
                                                .anyMatch(
                                                        violatorsLc::contains
                                                )
                                ).collect(toList())
                ).build();
        model.setActualThreats(
                model.getActualThreats().stream()
                        .peek(threat -> threat.setViolators(
                                violatorRepository.findByLcId(threat.getViolators()).stream()
                                        .distinct()
                                        .collect(toList())
                        )).collect(toList())
        );

        return nextProcessor.report(model);
    }
}
