package br.com.atlantico.demoteste.exception;

public class AuthorityNotFoundRuntimeException extends RuntimeException {
    public AuthorityNotFoundRuntimeException(String message) {
        super(message);
    }
}
