package ru.beerwaroff.pdstm.service;

import org.springframework.core.io.FileSystemResource;

public interface DownloadModelService {
    FileSystemResource download(String link);
}
