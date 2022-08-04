package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.Main;
import cz.cvut.kbss.ear.sis.enviroment.Generator;
import cz.cvut.kbss.ear.sis.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = Main.class)
public class UserDaoTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao sut;

    @Test
    public void findByUsernameReturnsPersonWithMatchingUsername() {
        final User user = Generator.generateUser();
        em.persist(user);

        final User result = sut.findByUsername(user.getUsername());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void findByUsernameReturnsNullForUnknownUsername() {
        Assertions.assertNull(sut.findByUsername("unknownUsername"));
    }

}
