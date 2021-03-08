package br.com.atlantico.demoteste.service;

import br.com.atlantico.demoteste.exception.AuthorityNotFoundRuntimeException;
import br.com.atlantico.demoteste.model.user.Authority;
import br.com.atlantico.demoteste.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {

    private AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority getAuthorityBy(String name) {
        Optional<Authority> authority = this.authorityRepository.findByName(name);

        if (authority.isPresent())
            return authority.get();
        else throw new AuthorityNotFoundRuntimeException("Regra de acesso não encontrada.");
    }
}
