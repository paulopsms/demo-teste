package br.com.atlantico.demoteste.user;

import br.com.atlantico.demoteste.dto.user.UserForCreateRequestDto;
import br.com.atlantico.demoteste.model.user.User;
import br.com.atlantico.demoteste.repository.UserRepository;
import br.com.atlantico.demoteste.service.AuthorityService;
import br.com.atlantico.demoteste.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ListUsersTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityService authorityService;

    private User userForTest;
    private User userForTest2;
    private User userForTest3;
    private UserService userService;
    private UserForCreateRequestDto userForCreateRequestDtoForTest;
    private UserForCreateRequestDto userForCreateRequestDtoForTest2;
    private UserForCreateRequestDto userForCreateRequestDtoForTest3;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository, authorityService);
        userForCreateRequestDtoForTest = UserRequestDtoBuilder.createUserRequestDtoForTest();
        userForCreateRequestDtoForTest2 = UserRequestDtoBuilder.createUserRequestDtoForTest2();
        userForCreateRequestDtoForTest3 = UserRequestDtoBuilder.createUserRequestDtoForTest3();
        this.userForTest = new User(this.userForCreateRequestDtoForTest);
        this.userForTest2 = new User(this.userForCreateRequestDtoForTest2);
        this.userForTest3 = new User(this.userForCreateRequestDtoForTest3);
    }

    @Test
    void deveListarUsuarios() {
        Mockito.when(this.userRepository.findAll()).thenReturn(this.getAllUsers());

        List<User> users = this.userService.listUsers();

        Assert.assertEquals(users.size(), 3);
        Assert.assertEquals("Juliana Teixeira", users.get(0).getName());
        Assert.assertEquals("Marcelo Carneiro", users.get(1).getName());
        Assert.assertEquals("Paulo Sergio", users.get(2).getName());

        Mockito.verify(userRepository).findAll();
    }

    private List<User> getAllUsers() {
        List<User> users = Arrays.asList(userForTest, userForTest2, userForTest3);

        users.sort(Comparator.comparing(User::getName));

        return users;
    }
}
