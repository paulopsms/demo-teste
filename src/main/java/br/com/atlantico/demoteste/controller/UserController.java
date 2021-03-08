package br.com.atlantico.demoteste.controller;

import br.com.atlantico.demoteste.dto.UserForUpdateDataRequestDto;
import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;
import br.com.atlantico.demoteste.dto.user.UserPasswordDto;
import br.com.atlantico.demoteste.dto.user.UserResponseDto;
import br.com.atlantico.demoteste.model.user.User;
import br.com.atlantico.demoteste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint para criação de novo usuário.
     *
     * @param userForCreateRequestDto - Objeto contendo informações do usuário a ser criado.
     * @return Objeto Json do usuário criado.
     */
    @PostMapping
    public ResponseEntity createNewUser(@RequestBody @Valid UserForCreateRequestDto userForCreateRequestDto) {
        User user = this.userService.createUser(userForCreateRequestDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserResponseDto(user));
    }

    /**
     * Endpoint para listagem de todos os usuários.
     *
     * @return Objeto Json dos usuários.
     */
    @GetMapping
    public ResponseEntity listAllUsers() {
        List<User> users = this.userService.listUsers();

        List<UserResponseDto> usersResponse = users.stream().map(UserResponseDto::new).collect(toList());

        return ResponseEntity.ok(usersResponse);
    }

    /**
     * Endpoint para busca de um usuário através de seu identificador.
     *
     * @param idUsuario - Id do usuário a ser buscado.
     * @return Objeto Json do usuário buscado.
     */
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("{idUsuario}")
    public ResponseEntity findUser(@PathVariable("idUsuario") Long idUsuario) {
        User user = this.userService.getUser(idUsuario);

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    /**
     * Endpoint para edição de usuário.
     *
     * @param idUsuario                   - Id do usuário a ser editado.
     * @param userForUpdateDataRequestDto - Objeto contendo informações do usuário a ser editado.
     * @return Objeto Json do usuário editado.
     */
    @PutMapping("{idUsuario}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity updateUser(@PathVariable("idUsuario") Long idUsuario,
                                     @RequestBody @Valid UserForUpdateDataRequestDto userForUpdateDataRequestDto) {
        User user = this.userService.updateUser(idUsuario, userForUpdateDataRequestDto);

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    /**
     * Endpoint para edição de senha de usuário.
     *
     * @param idUsuario       - Id do usuário na qual a senha será atualizada.
     * @param userPasswordDto - Objeto contendo informações do usuário a ser atualizado.
     * @return Objeto Json do usuário atualizado.
     */
    @PutMapping("{idUsuario}/password")
    public ResponseEntity updateUserPassword(@PathVariable("idUsuario") Long idUsuario,
                                             @RequestBody @Valid UserPasswordDto userPasswordDto) {
        User user = this.userService.updateUserPassword(idUsuario, userPasswordDto);

        return ResponseEntity.ok(new UserResponseDto(user));
    }

    /**
     * Endpoint para remoção de um usuário através de seu identificador.
     *
     * @param idUsuario - Id do usuário a ser removido
     * @return Objeto Json do usuário removido.
     */
    @DeleteMapping("{idUsuario}")
    public ResponseEntity deleteUser(@PathVariable("idUsuario") Long idUsuario) {
        User user = this.userService.deleteUser(idUsuario);

        return ResponseEntity.ok(new UserResponseDto(user));
    }
}
