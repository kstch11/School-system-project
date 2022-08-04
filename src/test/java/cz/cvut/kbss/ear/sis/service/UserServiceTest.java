package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.UserDao;
import cz.cvut.kbss.ear.sis.enviroment.Generator;
import cz.cvut.kbss.ear.sis.model.Role;
import cz.cvut.kbss.ear.sis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    //private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    private UserDao userDaoMock;

    private UserService service;

    private User user;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        this.service = new UserService(userDaoMock, passwordEncoder);
    }
    @Test
    public void findByUserName() {
        final User owner = Generator.generateUser();

        service.persist(owner);
        //final User result = service.getUser("username@kbss.felk.cvut.cz");

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        assertNotNull(captor.getValue().getUsername());
    }

    @Test
    public void persistSetsUserRoleToDefaultWhenItIsNotSpecified() {
        final User user = Generator.generateUser();
        user.setRole(null);
        service.persist(user);

        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        Assertions.assertEquals(Role.USER, captor.getValue().getRole());
    }

}
