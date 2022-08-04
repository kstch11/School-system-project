package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.ScheduleDao;
import cz.cvut.kbss.ear.sis.dao.SubjectDao;
import cz.cvut.kbss.ear.sis.dao.TeacherDao;
import cz.cvut.kbss.ear.sis.dao.UserDao;
import cz.cvut.kbss.ear.sis.enviroment.Generator;
import cz.cvut.kbss.ear.sis.model.Schedule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
//@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {
    @PersistenceContext
    private EntityManager em;

    @Mock
    private ScheduleDao scheduleDao;

    @Mock
    private TeacherDao teacherDao;

    @Mock
    private SubjectDao subjectDao;

    @Mock
    private UserDao userDao;

    private ScheduleService scheduleService;

    @BeforeEach
    public  void setUp() {this.scheduleService = new ScheduleService(scheduleDao, teacherDao, subjectDao, userDao);}

    @Test
    public void createScheduleByTeacherCreatesScheduleNotNull() {
        final Schedule schedule = scheduleService.createScheduleByTeacher(Generator.generateTeacher(), Generator.generateSubject(), Generator.generateClassroom(), "Mon, 09:15");
//        final Schedule toCreate = new Schedule();
//        toCreate.setId(schedule.getId());
//        toCreate.setClassroom(Generator.generateClassroom());
//        toCreate.setOwner(123);
//        toCreate.setSubject(Generator.generateSubject());
//        toCreate.setTime("Mon, 09:15");
        //final ArgumentCaptor<Schedule> captor1 = ArgumentCaptor.forClass(Schedule.class);
//        final ArgumentCaptor<Teacher> captor2 = ArgumentCaptor.forClass(Teacher.class);
//        final ArgumentCaptor<Subject> captor3 = ArgumentCaptor.forClass(Subject.class);
        //verify(scheduleDao).persist(captor1.capture());
        Assertions.assertNotNull(schedule);
    }
}
