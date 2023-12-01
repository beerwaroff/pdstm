package ru.beerwaroff.personaldatasecuritythreatmodelservice.mapper;

import org.mapstruct.Mapper;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.AuthenticationRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.dto.EmailRequest;
import ru.beerwaroff.personaldatasecuritythreatmodelservice.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(AuthenticationRequest request);
    User map(EmailRequest request);
}
