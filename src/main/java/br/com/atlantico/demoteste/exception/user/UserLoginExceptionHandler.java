package br.com.atlantico.demoteste.exception.user;

import br.com.atlantico.demoteste.dto.exception.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserLoginExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserLoginRuntimeException.class)
    public ExceptionDto handle(UserLoginRuntimeException exception) {
        String message = exception.getLocalizedMessage();

        return new ExceptionDto("Campos Inválidos", message);
    }
}
