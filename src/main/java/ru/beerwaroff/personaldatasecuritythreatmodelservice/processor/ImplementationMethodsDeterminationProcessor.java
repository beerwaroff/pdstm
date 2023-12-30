package ru.beerwaroff.personaldatasecuritythreatmodelservice.processor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.InfluenceObject;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ThreatModel;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.Violator;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.ImplementationMethodRepository;

import java.util.HashSet;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class ImplementationMethodsDeterminationProcessor {
    private final ImplementationMethodRepository repository;
    private final ActualThreatDeterminationProcessor nextProcessor;

    public ThreatModel determine(ThreatModel model) {
        val objects = model.getInfluenceObjects().stream()
                .map(InfluenceObject::getName)
                .collect(toList());
        val violators = model.getViolators().stream()
                .map(Violator::getName)
                .collect(toList());
        return nextProcessor.determine(
                model.toBuilder()
                        .methods(
                                repository.findAll().stream()
                                        .filter(
                                                method -> new HashSet<>(objects).contains(method.getObject())
                                                        && violators.contains(method.getViolator())
                                        ).collect(toList())
                        ).build()
        );
    }
}
