package br.com.atlantico.demoteste.dto.exception;

public class ExceptionDto {
    private String error;
    private String message;

    public ExceptionDto() {
    }

    public ExceptionDto(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
