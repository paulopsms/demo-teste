package br.com.atlantico.demoteste.user;

import br.com.atlantico.demoteste.dto.UserForUpdateDataRequestDto;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class UpdateUserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityService authorityService;

    private UserService userService;
    private UserForCreateRequestDto userForCreateRequestDtoForTest;
    private UserForUpdateDataRequestDto userForCreateRequestDtoForUpdateTest;
    private UserForCreateRequestDto userForCreateRequestDtoForFailureTest;
    private User userForTest;
    private Validator validator;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    ArgumentCaptor<Long> userIdCaptor;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository, authorityService);
        this.userForCreateRequestDtoForTest = UserRequestDtoBuilder.createUserRequestDtoForTest();
        this.userForCreateRequestDtoForUpdateTest = UserForUpdateDataRequestDtoBuilder.createUserRequestDtoForUpdateTest();
        this.userForCreateRequestDtoForFailureTest = UserRequestDtoBuilder.createUserForFailureTest();
        this.userForTest = new User(this.userForCreateRequestDtoForTest);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    void deveAtualizarDadosDoUsuario() {
        Set<ConstraintViolation<UserForUpdateDataRequestDto>> violations = validator.validate(userForCreateRequestDtoForUpdateTest);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(userForTest));

        User user = this.userService.updateUser(1L, userForCreateRequestDtoForUpdateTest);

        Mockito.verify(userRepository).findById(Mockito.any());
        Mockito.verify(userRepository).save(userCaptor.capture());

        Assert.assertTrue(violations.isEmpty());
        Assert.assertEquals("paulosergio", user.getLogin());
        Assert.assertEquals("paulo_sergio@atlantico.com.br", user.getEmail());
        Assert.assertEquals("Paulo Sergio Maia", user.getName());
        Assert.assertFalse(user.getAdmin());
        Assert.assertEquals(LocalDate.now(), user.getUpdatedDate().toLocalDate());
    }


    /**
     * Usuario userForCreateRequestDtoForFailureTest não possui informação de login.
     */
    @Test
    void deveFalharTentarAtualizarUsuarioComDadosInvalidos() {
        Set<ConstraintViolation<UserForCreateRequestDto>> violations = validator.validate(userForCreateRequestDtoForFailureTest);

        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    void deveFalharAoNaoEncontrarOUsuarioSolicitadoParaAtualizar() {
        Mockito.when(userRepository.findById(Mockito.any())).thenThrow(UserNotFoundException.class);

        try {
            User user = this.userService.updateUser(1L, userForCreateRequestDtoForUpdateTest);

            Mockito.verify(userRepository).findById(1L);
            Mockito.verifyNoInteractions(userRepository.save(userCaptor.capture()));
        } catch (UserNotFoundException e) {
        }
    }
}
