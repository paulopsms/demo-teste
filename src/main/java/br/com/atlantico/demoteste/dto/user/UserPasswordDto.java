package br.com.atlantico.demoteste.dto.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserPasswordDto {
    @NotNull(message = "Obrigatório informar uma senha para o usuário.")
    @Size(min = 4, max = 30, message = "A senha deve conter no mínimo 4 caracteres e no máximo 30.")
    private String password;

    public UserPasswordDto() {
    }

    public UserPasswordDto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
