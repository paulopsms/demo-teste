package br.com.atlantico.demoteste.dto.user;

import br.com.atlantico.demoteste.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserResponseDto implements Serializable {
    private Long id;
    private String name;
    private String login;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;
    private String email;
    private Boolean admin;

    public UserResponseDto() {
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.createdDate = user.getCreatedDate();
        this.updatedDate = user.getUpdatedDate();
        this.email = user.getEmail();
        this.admin = user.getAdmin();
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getAdmin() {
        return admin;
    }
}
