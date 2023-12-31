package ru.beerwaroff.personaldatasecuritythreatmodelservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.ThreatModelRequestProxy;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.handler.ThreatModelHandler;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.mapper.ThreatModelRequestMapper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.DownloadModelService;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.ControllerConstant.DOWNLOAD_PDF_MODEL_URL;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.SymbolConstant.SLASH;

@Controller
@RequiredArgsConstructor
public class ThreatModelController {
    private final String REQUEST_MODEL_ATTRIBUTE = "request";
    private final ThreatModelHandler handler;
    private final ThreatModelRequestMapper requestMapper;
    private final DownloadModelService downloadService;

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

    @RequestMapping(method = GET, value = DOWNLOAD_PDF_MODEL_URL, produces = {"application/pdf; charset=Windows-1251"})
    @ResponseBody
    public FileSystemResource download(@PathVariable String link) {
        return downloadService.download(link);
    }
}
