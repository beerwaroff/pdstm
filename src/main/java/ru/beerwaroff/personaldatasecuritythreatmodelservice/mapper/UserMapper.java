package ru.beerwaroff.personaldatasecuritythreatmodelservice.mapper;

import org.mapstruct.Mapper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.request.EmailRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(EmailRequest request);
}
