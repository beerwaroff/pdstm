package ru.beerwaroff.pdstm.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import ru.beerwaroff.pdstm.service.DownloadModelService;

import java.io.File;

import static ru.beerwaroff.pdstm.constant.SymbolConstant.SLASH;

@Service
@RequiredArgsConstructor
public class DownloadModelServiceImpl implements DownloadModelService {

    @Value("${threatModelFolder}")
    private String folder;

    @Override
    public FileSystemResource download(String link) {
        val file = new File(folder + SLASH + link);
        return new FileSystemResource(
                file
        );
    }
}
