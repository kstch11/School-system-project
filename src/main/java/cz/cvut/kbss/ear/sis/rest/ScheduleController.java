package cz.cvut.kbss.ear.sis.rest;

import cz.cvut.kbss.ear.sis.model.*;
import cz.cvut.kbss.ear.sis.rest.auth.LoginController;
import cz.cvut.kbss.ear.sis.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final SubjectReservationService subjectReservationService;
    private final SubjectService subjectService;
    private final ClassroomService classroomService;
    @Autowired
    public ScheduleController(ScheduleService scheduleService, UserService userService, SubjectReservationService subjectReservationService, SubjectService subjectService, ClassroomService classroomService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.subjectReservationService = subjectReservationService;
        this.subjectService = subjectService;
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACHER')")
    @GetMapping("/{userName}")
    public List<Schedule> returnScheduleByUserName(@PathVariable String userName) {
        Integer owner = userService.getUser(userName).getId();
        return scheduleService.getScheduleByOwner(owner);

    }

    /**
     * Create schedule by Teacher
     */

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("/{UserName}/{subjectId}/{classroomNumber}")
    public ResponseEntity<Map> createSchedule(@PathVariable String UserName,
                                              @PathVariable Integer subjectId,
                                              @PathVariable Integer classroomNumber,
                                              @RequestParam("time") String time) {
        Teacher teacher = (Teacher) userService.getUser(UserName);
        Subject sub = subjectService.find(subjectId);
        Classroom classroom = classroomService.find(classroomNumber);

        Schedule schedule = scheduleService.createScheduleByTeacher(teacher, sub, classroom, time);
        if (schedule == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("scheduleId", schedule.getId());
        returnMap.put("subject", schedule.getSubject());
        returnMap.put("teacher", schedule.getOwner());
        returnMap.put("time", schedule.getTime());
        returnMap.put("classroom", schedule.getClassroom());
        return new ResponseEntity<>(returnMap, null, HttpStatus.CREATED);
    }

    /**
    Create schedule by Student
     */

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @PostMapping("/createfromres/{subject}/{subResId}/{teacherId}/{userId}")
    public ResponseEntity<Map> createSchedule(@PathVariable Integer subResId,
                                   @PathVariable Integer userId,
                                   @PathVariable Integer teacherId,
                                   @PathVariable String subject) {
        Schedule schedule = new Schedule();
        int var = 0;
        List<SubjectReservation> subjectReservation = subjectReservationService.getReservationsByOwner(userId);
        List<Schedule> scheduleList = scheduleService.getScheduleByOwner(teacherId);
        for (int j = 0; scheduleList.size() > j; j++) {
            if (scheduleList.get(j).getSubject().getTitle() == subject) {
                var = j;
            }
        }
        for (int i = 0; subjectReservation.size() > i; i++) {
            if (subjectReservation.get(i).getId() == subResId
                    && scheduleList.get(var) != null) {
                schedule = scheduleService.createScheduleByStudent(subjectReservation.get(i), teacherId);
                subjectReservationService.remove(subjectReservation.get(i));

            }
        }
        if (schedule == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("scheduleId", schedule.getId());
        returnMap.put("subject", schedule.getSubject());
        returnMap.put("teacher", schedule.getOwner());
        returnMap.put("time", schedule.getTime());
        returnMap.put("classroom", schedule.getClassroom());
        return new ResponseEntity<>(returnMap, null, HttpStatus.CREATED);


    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping(value = "/deleteschedule/{scheduleId}")
    public void deleteSchedule(@PathVariable Integer scheduleId,
                               @RequestParam("user") Integer user) {
        List<Schedule> schedules = scheduleService.getScheduleByOwner(user);
        for (int i = 0; schedules.size() > i; i++) {
            if (schedules.get(i).getId() == scheduleId) {
                scheduleService.remove(schedules.get(i));
                LOG.trace("Schedule cell was successfully deleted");
            }
        }
    }

}
