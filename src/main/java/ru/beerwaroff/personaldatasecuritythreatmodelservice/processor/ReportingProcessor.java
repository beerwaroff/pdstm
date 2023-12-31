package ru.beerwaroff.personaldatasecuritythreatmodelservice.processor;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.config.property.ServerProperty;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.ThreatModel;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.generator.PdfGenerator;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.parser.ThymeleafParser;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.service.MailSenderService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.ControllerConstant.DOWNLOAD_URL;
import static ru.beerwaroff.personaldatasecuritythreatmodelservice.constant.SymbolConstant.SLASH;

@Component
@RequiredArgsConstructor
public class ReportingProcessor {
    private final MailSenderService mailSenderService;
    private final ServerProperty property;
    private final PdfGenerator pdfGenerator;
    private final ThymeleafParser thymeleafParser;

    @Value("${threatModelFolder}")
    private String folder;

    @Transactional
    public ThreatModel report(ThreatModel model) {
        val link = randomUUID().toString() + ".pdf";
        this.createPdf(model, link);
        this.send(model, link);
        return model.toBuilder()
                .link(link)
                .build();
    }

    private void createPdf(ThreatModel model, String link) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("threatModel", model);
        try {
            pdfGenerator.generate(
                    thymeleafParser.parse(
                            "templates/tm_report", variables
                    ), folder + SLASH + link
            );
        } catch (IOException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private void send(ThreatModel model, String link) {
        mailSenderService.send(
                model.getUsername(),
                "Модель угроз.",
                "Ссылка для скачивания модели угроз: " + property.getHost() + property.getPort()
                        + DOWNLOAD_URL + SLASH + link
        );
    }
}
