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
public class QuizAnswers {
    private String isGovernmental = "false";
    private String isAccessRemovable = "false";
    private String isProtect = "false";
    private String isFinancial = "false";
    private String isExternal = "false";
    private String hasDevelopers = "false";
    private String hasOpponents = "false";
    private String isAccessKz = "false";
}
