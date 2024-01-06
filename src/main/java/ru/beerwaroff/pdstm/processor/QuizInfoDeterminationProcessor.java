package ru.beerwaroff.pdstm.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.beerwaroff.pdstm.dto.request.ThreatModelRequest;
import ru.beerwaroff.pdstm.dto.ModelCatalog;
import ru.beerwaroff.pdstm.dto.ThreatModel;
import ru.beerwaroff.pdstm.repository.InfluenceObjectRepository;
import ru.beerwaroff.pdstm.repository.ModelCatalogRecordRepository;
import ru.beerwaroff.pdstm.repository.NegativeConsequencesRepository;
import ru.beerwaroff.pdstm.repository.ViolatorRepository;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Component
@RequiredArgsConstructor
public class QuizInfoDeterminationProcessor {
    private final ModelCatalogRecordRepository modelCatalogRecordRepository;
    private final NegativeConsequencesRepository negativeConsequencesRepository;
    private final InfluenceObjectRepository influenceObjectRepository;
    private final ViolatorRepository violatorRepository;
    private final ImplementationMethodsDeterminationProcessor nextProcessor;

    public ThreatModel determine(ThreatModelRequest request) {
        return nextProcessor.determine(
                ThreatModel.builder()
                        .username(getContext().getAuthentication().getName())
                        .info(request.getInfo())
                        .catalog(
                                ModelCatalog.builder()
                                        .riskTypes(modelCatalogRecordRepository.findRiskTypes())
                                        .negativeConsequences(modelCatalogRecordRepository.findNegativeConsequences())
                                        .impactTypes(modelCatalogRecordRepository.findImpactTypes())
                                        .violatorTypes(modelCatalogRecordRepository.findViolatorTypes())
                                        .implementationMethods(modelCatalogRecordRepository.findImplementationMethods())
                                        .threats(modelCatalogRecordRepository.findThreats())
                                        .build()
                        ).negativeConsequences(
                                negativeConsequencesRepository.find(
                                        request.getNegativeConsequencesFilter()
                                )
                        ).influenceObjects(
                                influenceObjectRepository.findAll(
                                        request.getInfluenceObjectsFilter()
                                )
                        ).violators(
                                violatorRepository.findAll(
                                        request.getViolatorFilter()
                                )
                        ).build()
        );
    }
}
