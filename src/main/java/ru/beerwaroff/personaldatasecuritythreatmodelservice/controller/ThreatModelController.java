package ru.beerwaroff.personaldatasecuritythreatmodelservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.ThreatModelRequestProxy;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.handler.ThreatModelHandler;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.mapper.ThreatModelRequestMapper;

import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.SymbolConstant.SLASH;

@Controller
@RequiredArgsConstructor
public class ThreatModelController {
    private final String REQUEST_MODEL_ATTRIBUTE = "request";
    private final ThreatModelHandler handler;
    private final ThreatModelRequestMapper requestMapper;

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute(REQUEST_MODEL_ATTRIBUTE, new ThreatModelRequestProxy());
        return "index";
    }

    @PostMapping(SLASH)
    public String generate(@ModelAttribute(REQUEST_MODEL_ATTRIBUTE) ThreatModelRequestProxy request, Model model) {
        model.addAttribute(
                "threatModel",
                handler.handle(
                        requestMapper.map(request)
                )
        );
        return "threat_model";
    }
}
