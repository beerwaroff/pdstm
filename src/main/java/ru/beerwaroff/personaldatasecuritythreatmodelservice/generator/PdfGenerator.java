package ru.beerwaroff.personaldatasecuritythreatmodelservice.generator;

import com.lowagie.text.DocumentException;
import lombok.val;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Objects;

@Component
public class PdfGenerator {

    public void generate(String html, String path) throws IOException, DocumentException {
        val stream = new FileOutputStream(path);
        val renderer = new ITextRenderer();
        val sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);
        renderer.getFontResolver().addFont(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource("fonts/calibri.ttf")
                ).toString(), "Windows-1251", true
        );
        val baseUrl = FileSystems.getDefault()
                .getPath("src/main/resources/")
                .toUri().toURL().toString();
        renderer.setDocumentFromString(html, baseUrl);
        renderer.layout();
        renderer.createPDF(stream);
        stream.close();
    }
}
