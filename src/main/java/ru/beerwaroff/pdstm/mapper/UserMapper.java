package ru.beerwaroff.pdstm.mapper;

import org.mapstruct.Mapper;
import ru.beerwaroff.pdstm.dto.request.EmailRequest;
import ru.beerwaroff.pdstm.dto.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(EmailRequest request);
}
