package cz.cvut.kbss.ear.sis.config;

import cz.cvut.kbss.ear.sis.model.*;
import cz.cvut.kbss.ear.sis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitialData implements ApplicationRunner {
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final SubjectReservationService subjectReservationService;
    private final SubjectService subjectService;
    private final ClassroomService classroomService;
    private final PasswordEncoder passwordEncoder;
    private Subject subject;

    @Autowired
    public InitialData(UserService userService, ScheduleService scheduleService, SubjectReservationService subjectReservationService, SubjectService subjectService, ClassroomService classroomService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.subjectReservationService = subjectReservationService;
        this.subjectService = subjectService;
        this.classroomService = classroomService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAdminUser("Admin", "Admin");
        createSubject("EAR", 2, 40);
        createSubject("OMO", 3, 40);
        createSubject("PJC", 4, 40);
        createSubject("MAA", 5, 40);
        createSubject("PSI", 6, 40);
        createClassroom(45);
        createClassroom(46);
        createClassroom(47);
        //createClassroom(50);
        createSimpleScheduleForOneSubject(createTeacherUser("Teacher", "Teacher"), 45, 2, "Fr 4:00 pm");
        createSimpleScheduleForOneSubject(createTeacherUser("Teacher1", "Teacher1"), 46, 3, "Fr 4:00 pm");
        createSimpleScheduleForOneSubject(createTeacherUser("Teacher2", "Teacher2"), 47, 4, "Fr 4:00 pm");
        createSubjectReservationForOneSubject(createStudentUser("Student", "Student"), 45, 2);
        createSubjectReservationForOneSubject(createStudentUser("Student1", "Student1"), 46, 3);
        createSubjectReservationForOneSubject(createStudentUser("Student2", "Student2"), 47, 4);
    }

    private Teacher createTeacherUser(String username, String password) {
        final Teacher teacher = new Teacher();
        teacher.setRole(Role.TEACHER);
        teacher.setUsername(username);
        teacher.setName("Teacher");
        teacher.setSurname("Teacherovi4");
        teacher.setPassword(password);
        userService.createUser(teacher);
        return teacher;
    }

    private void createAdminUser(String username, String password) {
        final Admin admin = new Admin();
        admin.setName("Admin");
        admin.setPassword(password);
        admin.setRole(Role.ADMIN);
        admin.setSurname("Pavlovi4");
        //user.setId(1);
        admin.setUsername(username);
        userService.createUser(admin);
    }

    private Student createStudentUser(String username, String password) {
        final Student student = new Student();
        student.setName("Student");
        student.setPassword(password);
        student.setRole(Role.STUDENT);
        student.setSurname("Studentovi4");
        //user.setId(1);
        student.setUsername(username);
        userService.createUser(student);
        return student;
    }

    private void createSimpleScheduleForOneSubject(Teacher teacher, Integer classroom, int subjectId, String time) {
        final Schedule schedule = new Schedule();
        schedule.setSubject(subjectService.find(subjectId));
        schedule.setClassroom(classroomService.find(classroom));
        schedule.setTime(time);
        schedule.setOwner(teacher.getId());
        scheduleService.persist(schedule);
    }

    private void createSubjectReservationForOneSubject(Student student, Integer classroom, int subjectId) {
        final SubjectReservation subjectReservation = new SubjectReservation();
        subjectReservation.setSubject(subjectService.find(subjectId));
        subjectReservation.setClassroom(classroomService.find(classroom));
        subjectReservation.setStudent(student);

        subjectReservationService.persist(subjectReservation);
    }

    private Subject createSubject(String title, Integer id, Integer capacity) {
        final Subject subject = new Subject();
        subject.setId(id);
        subject.setTitle(title);
        subject.setCapacity(capacity);
        subjectService.persist(subject);
        return subject;
    }

    private Classroom createClassroom(Integer classroomNumber) {
        final Classroom classroom = new Classroom();
        classroom.setNumber(classroomNumber);
        classroomService.persist(classroom);
        return classroom;
    }
}
