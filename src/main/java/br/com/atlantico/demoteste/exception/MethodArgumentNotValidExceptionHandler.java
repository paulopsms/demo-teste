package br.com.atlantico.demoteste.exception;

import br.com.atlantico.demoteste.dto.exception.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    private Logger log = LoggerFactory.getLogger(MethodArgumentNotValidExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionDto handle(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrorList = exception.getBindingResult().getFieldErrors();

        FieldError fieldError = fieldErrorList.get(0);

        String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

        return new ExceptionDto("Campos Inválidos", message);
    }
}
