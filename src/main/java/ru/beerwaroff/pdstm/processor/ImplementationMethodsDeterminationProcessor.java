package ru.beerwaroff.pdstm.processor;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import ru.beerwaroff.pdstm.dto.InfluenceObject;
import ru.beerwaroff.pdstm.dto.ThreatModel;
import ru.beerwaroff.pdstm.dto.Violator;
import ru.beerwaroff.pdstm.repository.ImplementationMethodRepository;

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
