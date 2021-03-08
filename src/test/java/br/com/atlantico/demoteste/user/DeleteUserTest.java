package br.com.atlantico.demoteste.user;

import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;
import br.com.atlantico.demoteste.exception.user.UserNotFoundException;
import br.com.atlantico.demoteste.model.user.User;
import br.com.atlantico.demoteste.repository.UserRepository;
import br.com.atlantico.demoteste.service.AuthorityService;
import br.com.atlantico.demoteste.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;

public class DeleteUserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityService authorityService;

    private UserService userService;
    private UserForCreateRequestDto userForCreateRequestDtoForTest;
    private User userForTest;
    private Validator validator;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository, authorityService);
        this.userForCreateRequestDtoForTest = UserRequestDtoBuilder.createUserRequestDtoForTest();
        this.userForTest = new User(this.userForCreateRequestDtoForTest);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    void deveRemoverUsuarioComSucesso() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userForTest));

        User user = this.userService.deleteUser(1L);

        Assert.assertEquals("Paulo Sergio", user.getName());
        Assert.assertEquals("paulo", user.getLogin());
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).delete(Mockito.any());
    }

    @Test
    void deveFalharAoNaoEncontrarOUsuarioSolicitadoParaDeletar() {
        Mockito.when(userRepository.findById(Mockito.any())).thenThrow(UserNotFoundException.class);

        try {
            User user = this.userService.deleteUser(1L);

            Mockito.verify(userRepository).findById(1L);
            Mockito.verifyNoInteractions(userRepository.save(userCaptor.capture()));
        } catch (UserNotFoundException e) {
        }
    }
}
