package br.com.atlantico.demoteste.exception.user;

public class UserLoginRuntimeException extends RuntimeException {
    public UserLoginRuntimeException(String message) {
        super(message);
    }
}
