package br.com.atlantico.demoteste.exception.user;

public class UnauthorizerUserException extends RuntimeException {
    public UnauthorizerUserException(String s) {
        super(s);
    }
}
