package cz.cvut.kbss.ear.sis.rest;


import cz.cvut.kbss.ear.sis.model.User;
import cz.cvut.kbss.ear.sis.rest.auth.LoginController;
import cz.cvut.kbss.ear.sis.service.ScheduleService;
import cz.cvut.kbss.ear.sis.service.SubjectReservationService;
import cz.cvut.kbss.ear.sis.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final ScheduleService scheduleService;
    private final SubjectReservationService subjectReservationService;

    @Autowired
    public UserController(UserService userService, ScheduleService scheduleService, SubjectReservationService subjectReservationService) {
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.subjectReservationService = subjectReservationService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TEACHER', 'STUDENT')")
    @GetMapping("/current")
    public ResponseEntity<Map> getUserInfo() {
        Object userName = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userName.toString();
        User user = userService.getUser(username);
       // return
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("userId", user.getId());
        returnMap.put("userName", user.getUsername());
        returnMap.put("name", user.getName());
        returnMap.put("surname", user.getSurname());
        returnMap.put("role", user.getRole());
        return new ResponseEntity<>(returnMap, null, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'TEACHER', 'STUDENT')")
    @GetMapping("/{userName}")
    public User getUserInfoByUserName(@PathVariable String userName) {
        return userService.getUser(userName);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "/registrate")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        LOG.trace("User {} was created", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/deleteuser")
    public void deleteUser(@RequestParam("userName") String user) {
        if(scheduleService.getScheduleByOwner(userService.getUser(user).getId()) != null) {
            scheduleService.remove(scheduleService.getScheduleByOwner(userService.getUser(user).getId()));
        }
        if(subjectReservationService.getReservationsByOwner(userService.getUser(user).getId()) != null) {
            subjectReservationService.remove(subjectReservationService.getReservationsByOwner(userService.getUser(user).getId()));
        }
        userService.deleteUser(userService.getUser(user));
        LOG.trace("User {} was successfully deleted", user);
    }
}