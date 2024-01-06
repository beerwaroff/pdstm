package ru.beerwaroff.pdstm.exception.advice;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.beerwaroff.pdstm.dto.response.ExceptionResponse;
import ru.beerwaroff.pdstm.exception.NotFoundException;
import ru.beerwaroff.pdstm.exception.PasswordException;
import ru.beerwaroff.pdstm.exception.PasswordNotMatchException;
import ru.beerwaroff.pdstm.exception.UsernameAlreadyExistsException;

import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, UsernameNotFoundException.class})
    public String handleNotFoundException(Model model, Exception e) {
        model.addAttribute(
                "response",
                ExceptionResponse.builder()
                        .code(NOT_FOUND.value())
                        .message(e.getMessage())
                        .build()
        );
        return "exception_advice";
    }
    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler({PasswordException.class, AuthenticationServiceException.class})
    public String handlePasswordException(Model model, Exception e) {
        model.addAttribute(
                "response",
                ExceptionResponse.builder()
                        .code(UNAUTHORIZED.value())
                        .message(e.getMessage())
                        .build()
        );
        return "exception_advice";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
            ValidationException.class,
            MethodArgumentNotValidException.class,
            UsernameAlreadyExistsException.class,
            PasswordNotMatchException.class
    })
    public String handleBadRequest(Model model, Exception e) {
        model.addAttribute(
                "response",
                ExceptionResponse.builder()
                        .code(BAD_REQUEST.value())
                        .message(e.getMessage())
                        .build()
        );
        return "exception_advice";
    }

//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({RuntimeException.class})
//    public String handleException(Model model) {
//        model.addAttribute(
//                "response",
//                ExceptionResponse.builder()
//                        .code(INTERNAL_SERVER_ERROR.value())
//                        .message("Внутренняя ошибка сервера.")
//                        .build()
//        );
//        return "exception_advice";
//    }
}