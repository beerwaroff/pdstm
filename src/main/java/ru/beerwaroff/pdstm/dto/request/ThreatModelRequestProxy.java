package ru.beerwaroff.pdstm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThreatModelRequestProxy {
    private CustomerName names;
    private ProtectionResponsible responsible;
    private SystemStructure structure;
    private SystemInfo info;
    private QuizAnswers answers = new QuizAnswers();
}