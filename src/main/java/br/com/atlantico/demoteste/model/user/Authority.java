package br.com.atlantico.demoteste.model.user;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    public Authority() {
    }

    public Authority(String name, String description, String path) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
