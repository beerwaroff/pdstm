package ru.beerwaroff.personaldatasecuritythreatmodelservice.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.ThreatModelRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ModelCatalog;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ThreatModel;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.InfluenceObjectRepository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.ModelCatalogRecordRepository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.NegativeConsequencesRepository;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.repository.ViolatorRepository;

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
