package br.com.atlantico.demoteste.user;

import br.com.atlantico.demoteste.dto.UserForUpdateDataRequestDto;

public class UserForUpdateDataRequestDtoBuilder {
    private String name;
    private String login;
    private String email;
    private Boolean admin;


    public UserForUpdateDataRequestDtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserForUpdateDataRequestDtoBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserForUpdateDataRequestDtoBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserForUpdateDataRequestDtoBuilder setAdmin(Boolean admin) {
        this.admin = admin;
        return this;
    }

    public UserForUpdateDataRequestDto build() {
        return new UserForUpdateDataRequestDto(name, login, email, admin);
    }

    public static UserForUpdateDataRequestDto createUserRequestDtoForTest() {
        return new UserForUpdateDataRequestDtoBuilder()
                .setName("Paulo Sergio")
                .setLogin("paulo")
                .setEmail("paulo@atlantico.com.br")
                .setAdmin(true)
                .build();
    }

    public static UserForUpdateDataRequestDto createUserRequestDtoForTest2() {
        return new UserForUpdateDataRequestDtoBuilder()
                .setName("Juliana Teixeira")
                .setLogin("juliana")
                .setEmail("juliana@atlantico.com.br")
                .setAdmin(false)
                .build();
    }

    public static UserForUpdateDataRequestDto createUserRequestDtoForTest3() {
        return new UserForUpdateDataRequestDtoBuilder()
                .setName("Marcelo Carneiro")
                .setLogin("marcelo")
                .setEmail("marcelo@atlantico.com.br")
                .setAdmin(false)
                .build();
    }

    public static UserForUpdateDataRequestDto createUserForFailureTest() {
        return new UserForUpdateDataRequestDtoBuilder()
                .setName("Paulo Sergio")
                .setEmail("paulo@atlantico.com.br")
                .setAdmin(true)
                .build();
    }

    public static UserForUpdateDataRequestDto createUserRequestDtoForUpdateTest() {
        return new UserForUpdateDataRequestDtoBuilder()
                .setName("Paulo Sergio Maia")
                .setLogin("paulosergio")
                .setEmail("paulo_sergio@atlantico.com.br")
                .setAdmin(false)
                .build();
    }
}
