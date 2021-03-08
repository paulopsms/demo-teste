package br.com.atlantico.demoteste.model.user;

import br.com.atlantico.demoteste.dto.UserForUpdateDataRequestDto;
import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Obrigatório informar o nome.")
    private String name;

    @NotNull(message = "Obrigatório informar o login")
    private String login;

    @NotNull(message = "Obrigatório informar a senha.")
    private String password;

    @NotNull(message = "Obrigatório informar data.")
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @NotNull(message = "Obrigatório informar email.")
    private String email;

    @NotNull(message = "Obrigatório informar se usuário é Admin.")
    private Boolean admin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "users_authorities_user_id"),
            inverseJoinColumns = @JoinColumn(name = "users_authorities_authority_id"))
    private Set<Authority> authorities = new HashSet<>();

    public User() {
    }

    public User(UserForCreateRequestDto userForCreateRequestDto) {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.name = userForCreateRequestDto.getName();
        this.login = userForCreateRequestDto.getLogin();
        this.password = new BCryptPasswordEncoder().encode(userForCreateRequestDto.getPassword());
        this.email = userForCreateRequestDto.getEmail();
        this.admin = userForCreateRequestDto.getAdmin();
    }

    public void updateData(UserForUpdateDataRequestDto userForUpdateDataRequestDto) {
        this.updatedDate = LocalDateTime.now();
        this.name = userForUpdateDataRequestDto.getName();
        this.login = userForUpdateDataRequestDto.getLogin();
        this.email = userForUpdateDataRequestDto.getEmail();
        this.admin = userForUpdateDataRequestDto.getAdmin();
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

    public Set<Authority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
