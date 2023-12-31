package ru.beerwaroff.personaldatasecuritythreatmodelservice.parser;

import lombok.val;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Map;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

@Component
public class ThymeleafParser {

    public String parse(String template, Map<String, Object> variables) {
        val resolver = new ClassLoaderTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setTemplateMode(HTML);
        val engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        val context = new Context();
        context.setVariables(variables);
        return engine.process(template, context);
    }
}
