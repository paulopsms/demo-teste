package br.com.atlantico.demoteste.exception.user;

import br.com.atlantico.demoteste.dto.exception.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnauthorizedUserExceptionHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionDto handle(UserNotFoundException exception) {
        String message = exception.getLocalizedMessage();

        return new ExceptionDto("Usuário não encontrado.", message);
    }
}
