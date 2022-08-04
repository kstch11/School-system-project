package cz.cvut.kbss.ear.sis.rest.auth;

import cz.cvut.kbss.ear.sis.model.User;
import cz.cvut.kbss.ear.sis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegisterController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        userService.createUser(user);
        LOG.trace("User {} successfully registered", user.getUsername());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
