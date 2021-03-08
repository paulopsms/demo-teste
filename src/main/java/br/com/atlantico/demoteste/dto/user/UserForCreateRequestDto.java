package br.com.atlantico.demoteste.dto.user;

import javax.validation.constraints.NotNull;

public class UserForCreateRequestDto {
    @NotNull(message = "Obrigatório informar o nome.")
    private String name;

    @NotNull(message = "Obrigatório informar o login")
    private String login;

    @NotNull(message = "Obrigatório informar a senha.")
    private String password;

    @NotNull(message = "Obrigatório informar email.")
    private String email;

    @NotNull(message = "Obrigatório informar se usuário é Admin.")
    private Boolean admin;

    public UserForCreateRequestDto() {
    }

    public UserForCreateRequestDto(@NotNull(message = "Obrigatório informar o nome.") String name,
                                   @NotNull(message = "Obrigatório informar o login") String login,
                                   @NotNull(message = "Obrigatório informar a senha.") String password,
                                   @NotNull(message = "Obrigatório informar email.") String email,
                                   @NotNull(message = "Obrigatório informar se usuário é Admin.") Boolean admin
    ) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
