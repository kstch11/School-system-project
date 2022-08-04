package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.Main;
import cz.cvut.kbss.ear.sis.enviroment.Generator;
import cz.cvut.kbss.ear.sis.model.Classroom;
import cz.cvut.kbss.ear.sis.model.Schedule;
import cz.cvut.kbss.ear.sis.model.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackageClasses = Main.class)
public class BaseDaoTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private ClassroomDao classroomDao;

    @Autowired
    private ScheduleDao scheduleDao;


    @Test
    public void persistSavesAndRemovesClassroom() {
        final Classroom classroom = Generator.generateClassroom();
        em.persist(classroom);

        final Classroom result = classroomDao.findByNumber(classroom.getNumber());
        Assertions.assertNotNull(result.getNumber());
        Assertions.assertEquals(classroom.getNumber(), result.getNumber());

        em.remove(classroom);
        final Classroom result2 = classroomDao.findByNumber(classroom.getNumber());
        Assertions.assertNull(result2);

    }

    @Test
    public void persistSavesAndFindsSchedule() {
        final Subject subject = Generator.generateSubject();
        em.persist(subject);
        Integer id = subject.getId();
        Assertions.assertEquals(subject, em.find(Subject.class, id));
    }




}
