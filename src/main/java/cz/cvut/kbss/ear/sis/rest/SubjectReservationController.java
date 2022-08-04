package cz.cvut.kbss.ear.sis.rest;

import cz.cvut.kbss.ear.sis.model.*;
import cz.cvut.kbss.ear.sis.service.ClassroomService;
import cz.cvut.kbss.ear.sis.service.SubjectReservationService;
import cz.cvut.kbss.ear.sis.service.SubjectService;
import cz.cvut.kbss.ear.sis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class SubjectReservationController {
    private final SubjectReservationService subjectReservationService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final ClassroomService classroomService;

    @Autowired
    public SubjectReservationController(SubjectReservationService subjectReservationService, UserService userService, SubjectService subjectService, ClassroomService classroomService) {
        this.subjectReservationService = subjectReservationService;
        this.userService = userService;
        this.subjectService = subjectService;
        this.classroomService = classroomService;
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @GetMapping("/{userName}")
    public List<SubjectReservation> returnReservationsByUserName(@PathVariable String userName) {
        Integer student = userService.getUser(userName).getId();
        return subjectReservationService.getReservationsByOwner(student);
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'ADMIN')")
    @PostMapping("/create/{UserName}/{subjectId}/{classroomNumber}")
    public ResponseEntity<Map> createReservation(@PathVariable String UserName,
                                                 @PathVariable Integer subjectId,
                                                 @PathVariable Integer classroomNumber) {
        Student student = (Student) userService.getUser(UserName);
        Subject sub = subjectService.find(subjectId);
        Classroom classroom = classroomService.find(classroomNumber);
        SubjectReservation subjectReservation = subjectReservationService.createReservation(classroom, sub, student);
        if (subjectReservation == null) {
            return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("reservationId", subjectReservation.getId());
        returnMap.put("subject", subjectReservation.getSubject());
        returnMap.put("student", subjectReservation.getStudent());
        return new ResponseEntity<>(returnMap, null, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping("/grade/{userName}/{subjectId}/{grade}")
    public ResponseEntity<Map> addGradeOfSubject(@PathVariable String userName,
                                     @PathVariable Integer grade,
                                     @PathVariable Integer subjectId) {
        int j = 0;
        Integer student = userService.getUser(userName).getId();
        Subject sub = subjectService.find(subjectId);
        List<SubjectReservation> subjectReservation = subjectReservationService.getReservationsByOwner(student);
        Objects.requireNonNull(subjectReservation);
        for (int i = 0; subjectReservation.size() > i; i++) {
            if(subjectReservation.get(i).getSubject() == sub) {
                subjectReservation.get(i).setGrade(grade);
                subjectReservationService.update(subjectReservation.get(i));
                j = i;
            }
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("reservationId", subjectReservation.get(j).getId());
        returnMap.put("subject", subjectReservation.get(j).getSubject());
        returnMap.put("student", subjectReservation.get(j).getStudent());
        returnMap.put("grade", subjectReservation.get(j).getGrade());
        returnMap.put("classroom", subjectReservation.get(j).getClassroom());
        return new ResponseEntity<>(returnMap, null, HttpStatus.CREATED);

    }
}
