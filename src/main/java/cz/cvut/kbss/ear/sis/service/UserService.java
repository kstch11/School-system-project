package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.UserDao;
import cz.cvut.kbss.ear.sis.exception.AlreadyExistingUserException;
import cz.cvut.kbss.ear.sis.model.Role;
import cz.cvut.kbss.ear.sis.model.User;
import cz.cvut.kbss.ear.sis.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void createUser(User user) {
        Objects.requireNonNull(user);
        checkExistingUser(user);
        user.encodePassword(passwordEncoder);
        if (user.getRole() == null) {
            user.setRole(Constants.DEFAULT_ROLE);
        }
        userDao.persist(user);
    }

    @Transactional
    public User getUser(String username) {
        Objects.requireNonNull(username);
        return userDao.findByUsername(username);
    }

    @Transactional
    public void updateUser(User user) {
        Objects.requireNonNull(user);
        if (user.getPassword() != null) {
            user.encodePassword(passwordEncoder);
        }
        userDao.update(user);
    }

    @Transactional
    public void deleteUser(User user) {
        Objects.requireNonNull(user);
        userDao.remove(user);
    }


    public void persist(User user) {
        Objects.requireNonNull(user);
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        userDao.persist(user);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return userDao.findByUsername(username) != null;
    }

    private void checkExistingUser(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            throw new AlreadyExistingUserException("User with this username already exists!");
        }
    }


}
