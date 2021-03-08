package br.com.atlantico.demoteste.user;

import br.com.atlantico.demoteste.dto.UserForUpdateDataRequestDto;
import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;

public class UserRequestDtoBuilder {
    private String name;
    private String login;
    private String password;
    private String email;
    private Boolean admin;


    public UserRequestDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserRequestDtoBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserRequestDtoBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserRequestDtoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRequestDtoBuilder setAdmin(Boolean admin) {
        this.admin = admin;
        return this;
    }

    public UserForCreateRequestDto build() {
        return new UserForCreateRequestDto(name, login, password, email, admin);
    }

    public static UserForCreateRequestDto createUserRequestDtoForTest() {
        return new UserRequestDtoBuilder()
                .setName("Paulo Sergio")
                .setLogin("paulo")
                .setPassword("paulo")
                .setEmail("paulo@atlantico.com.br")
                .setAdmin(true)
                .build();
    }

    public static UserForCreateRequestDto createUserRequestDtoForTest2() {
        return new UserRequestDtoBuilder()
                .setName("Juliana Teixeira")
                .setLogin("juliana")
                .setPassword("juliana")
                .setEmail("juliana@atlantico.com.br")
                .setAdmin(false)
                .build();
    }

    public static UserForCreateRequestDto createUserRequestDtoForTest3() {
        return new UserRequestDtoBuilder()
                .setName("Marcelo Carneiro")
                .setLogin("marcelo")
                .setPassword("marcelo")
                .setEmail("marcelo@atlantico.com.br")
                .setAdmin(false)
                .build();
    }

    public static UserForCreateRequestDto createUserForFailureTest() {
        return new UserRequestDtoBuilder()
                .setName("Paulo Sergio")
                .setPassword("paulo")
                .setEmail("paulo@atlantico.com.br")
                .setAdmin(true)
                .build();
    }
}
