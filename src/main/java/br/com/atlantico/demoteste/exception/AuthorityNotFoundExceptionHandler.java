package br.com.atlantico.demoteste.exception;

import br.com.atlantico.demoteste.dto.exception.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthorityNotFoundExceptionHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AuthorityNotFoundRuntimeException.class)
    public ExceptionDto handle(AuthorityNotFoundRuntimeException exception) {
        String message = exception.getLocalizedMessage();

        return new ExceptionDto("Regra de acesso não encontrada.", message);
    }
}
