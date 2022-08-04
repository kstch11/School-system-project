package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.dao.BaseDao;
import cz.cvut.kbss.ear.sis.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
public class UserDao extends BaseDao<User> {
    public UserDao() {
        super(User.class);
    }

    @Transactional
    public User findByUsername(String username) {
        try {
            User temp = em.createNamedQuery("User.findByUsername", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

}
