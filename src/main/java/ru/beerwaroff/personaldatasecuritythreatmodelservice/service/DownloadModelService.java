package ru.beerwaroff.personaldatasecuritythreatmodelservice.service;

import org.springframework.core.io.FileSystemResource;

public interface DownloadModelService {
    FileSystemResource download(String link);
}
