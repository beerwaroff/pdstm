package ru.beerwaroff.pdstm.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.beerwaroff.pdstm.dto.request.ThreatModelRequest;
import ru.beerwaroff.pdstm.dto.ThreatModel;
import ru.beerwaroff.pdstm.processor.QuizInfoDeterminationProcessor;

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
