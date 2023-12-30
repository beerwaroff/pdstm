package ru.beerwaroff.personaldatasecuritythreatmodelservice.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.ThreatModelRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ThreatModel;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.processor.QuizInfoDeterminationProcessor;

@Component
@RequiredArgsConstructor
public class ThreatModelHandler {
    private final QuizInfoDeterminationProcessor nextProcessor;

    public ThreatModel handle(ThreatModelRequest request) {
        return this.startSimulation(request);
    }

    private ThreatModel startSimulation(ThreatModelRequest request) {
        return nextProcessor.determine(request);
    }
}
