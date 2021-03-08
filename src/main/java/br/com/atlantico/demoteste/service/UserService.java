package br.com.atlantico.demoteste.service;

import br.com.atlantico.demoteste.dto.UserForUpdateDataRequestDto;
import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;
import br.com.atlantico.demoteste.dto.user.UserPasswordDto;
import br.com.atlantico.demoteste.exception.user.UnauthorizerUserException;
import br.com.atlantico.demoteste.exception.user.UserLoginRuntimeException;
import br.com.atlantico.demoteste.exception.user.UserNotFoundException;
import br.com.atlantico.demoteste.model.user.Authority;
import br.com.atlantico.demoteste.model.user.User;
import br.com.atlantico.demoteste.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private UserRepository userRepository;
    private AuthorityService authorityService;

    @Autowired
    public UserService(UserRepository userRepository, AuthorityService authorityService) {
        this.authorityService = authorityService;
        this.userRepository = userRepository;
    }

    /**
     * Método que contém a regra de negócio para criação de novo usuário.
     *
     * @param userForCreateRequestDto - Objeto contendo informações do usuário a ser criado.
     * @return UserResponseDto - Objeto contendo as informações do usuário criado.
     */
    @Transactional
    public User createUser(UserForCreateRequestDto userForCreateRequestDto) {
        Optional<User> optionalUsuario = this.userRepository.findByLogin(userForCreateRequestDto.getLogin());

        if (optionalUsuario.isPresent())
            throw new UserLoginRuntimeException("O Login '" + userForCreateRequestDto.getLogin() + "' já existe.");

        User user = new User(userForCreateRequestDto);

        Set<Authority> authorities = new HashSet<>();

        Authority authority = this.authorityService.getAuthorityBy(USER);
        authorities.add(authority);

        if (user.getAdmin()) {
            authority = this.authorityService.getAuthorityBy(ADMIN);

            authorities.add(authority);
        }

        user.setAuthorities(authorities);
        this.userRepository.save(user);

        return user;
    }

    /**
     * Método que contém a regra de negócio para listagem de todos os usuários.
     *
     * @return List<UserResponseDto> - Lista de objetos contendo as informações dos usuários.
     */
    public List<User> listUsers() {
        List<User> users = this.userRepository.findAll();

        return users;
    }

    /**
     * Método que contém a regra de negócio para busca de um usuário.
     *
     * @return UserResponseDto - Objeto contendo as informações do usuário.
     */
    public User getUser(Long idUsuario) {
        User user = this.findUserBy(idUsuario);

        return user;
    }

    /**
     * Método que contém a regra de negócio para atualização de dados de um usuário.
     *
     * @param idUsuario                   - Identificador do usuário a ser alterado.
     * @param userForUpdateDataRequestDto - Objeto contendo informações do usuário a ser criado.
     * @return UserResponseDto - Objeto contendo as informações do usuário editado.
     */
    @Transactional
    public User updateUser(Long idUsuario, UserForUpdateDataRequestDto userForUpdateDataRequestDto) {
        User user = this.findUserBy(idUsuario);

        user.updateData(userForUpdateDataRequestDto);

        this.userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateUserPassword(Long idUsuario, UserPasswordDto userPasswordDto) {
        User user = this.findUserBy(idUsuario);

        if (!user.getAdmin())
            throw new UnauthorizerUserException("Usuário não possui permissão para alterar senha.");

        user.setPassword(userPasswordDto.getPassword());

        this.userRepository.save(user);

        return user;
    }

    /**
     * Método que contém a regra de negócio para remoção de um usuário.
     *
     * @param idUsuario - Identificador do usuário a ser removido.
     * @return UserResponseDto  - Objeto contendo as informações do usuário removido.
     */
    @Transactional
    public User deleteUser(Long idUsuario) {
        User user = this.findUserBy(idUsuario);

        this.userRepository.delete(user);

        return user;
    }

    // Método privado para busca de usuário no banco de dados
    private User findUserBy(Long idUsuario) {
        Optional<User> usuario = this.userRepository.findById(idUsuario);

        if (usuario.isPresent())
            return usuario.get();
        else
            throw new UserNotFoundException("Usuário não encontrado.");
    }
}
