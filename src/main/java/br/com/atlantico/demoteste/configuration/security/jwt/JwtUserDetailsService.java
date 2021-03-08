package br.com.atlantico.demoteste.configuration.security.jwt;

import br.com.atlantico.demoteste.model.user.User;
import br.com.atlantico.demoteste.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuarioOptional = this.userRepository.findByLogin(username);

        if (usuarioOptional.isPresent())
            return usuarioOptional.get();
        else throw new UsernameNotFoundException("Usuário " + username + " não encontrado.");
    }
}
