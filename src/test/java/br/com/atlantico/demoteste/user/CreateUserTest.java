package br.com.atlantico.demoteste.user;

import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;
import br.com.atlantico.demoteste.exception.user.UserLoginRuntimeException;
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
import java.util.Set;

class CreateUserTest {

    private static final String USER = "user";
    private static final String ADMIN = "admin";

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityService authorityService;

    private UserService userService;
    private UserForCreateRequestDto userForCreateRequestDtoForTest;
    private UserForCreateRequestDto userForCreateRequestDtoForFailureTest;
    private Validator validator;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository, authorityService);
        this.userForCreateRequestDtoForTest = UserRequestDtoBuilder.createUserRequestDtoForTest();
        this.userForCreateRequestDtoForFailureTest = UserRequestDtoBuilder.createUserForFailureTest();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        this.validator = validatorFactory.getValidator();
    }

    @Test
    void deveIncluirUsuario() {
        Set<ConstraintViolation<UserForCreateRequestDto>> violations = validator.validate(userForCreateRequestDtoForTest);

        User user = this.userService.createUser(userForCreateRequestDtoForTest);

        Assert.assertTrue(violations.isEmpty());
        Assert.assertEquals("paulo", user.getLogin());
        Assert.assertEquals("paulo@atlantico.com.br", user.getEmail());
        Assert.assertEquals("Paulo Sergio", user.getName());
        Assert.assertTrue(user.getAdmin());
        Assert.assertEquals(LocalDate.now(), user.getCreatedDate().toLocalDate());

        Mockito.verify(authorityService).getAuthorityBy(USER);
        Mockito.verify(authorityService).getAuthorityBy(ADMIN);
        Mockito.verify(userRepository).findByLogin(userForCreateRequestDtoForTest.getLogin());
        Mockito.verify(userRepository).save(userCaptor.capture());
    }


    /**
     * Usuario userForCreateRequestDtoForFailureTest não possui informação de login.
     */
    @Test
    void deveFalharValidacaoDeUsuarioComDadosInvalidos() {
        Set<ConstraintViolation<UserForCreateRequestDto>> violations = validator.validate(userForCreateRequestDtoForFailureTest);

        Assert.assertFalse(violations.isEmpty());
        Assert.assertEquals(violations.size(), 1);
    }

    @Test
    void deveFalharAoCadastrarUsuarioComLoginJaCadastradoAnteriormente() {
        Mockito.when(userRepository.findByLogin(Mockito.any())).thenThrow(UserLoginRuntimeException.class);

        try {
            User user = this.userService.createUser(userForCreateRequestDtoForTest);

            Mockito.verify(userRepository).findByLogin(userForCreateRequestDtoForTest.getLogin());
            Mockito.verifyNoInteractions(userRepository.save(userCaptor.capture()));
        } catch (UserLoginRuntimeException e) {
        }

    }
}
