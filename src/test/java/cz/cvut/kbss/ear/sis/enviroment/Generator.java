package cz.cvut.kbss.ear.sis.enviroment;

import cz.cvut.kbss.ear.sis.model.*;

import java.util.Random;

public class Generator {
    private static final Random RAND = new Random();

    public static int randomInt() {
        return RAND.nextInt();
    }

    public static boolean randomBoolean() {
        return RAND.nextBoolean();
    }

    public static User generateUser() {
        final User user = new User();
        user.setName("Name" + randomInt());
        user.setSurname("Surname" + randomInt());
        user.setUsername("username@kbss.felk.cvut.cz");
        user.setPassword(Integer.toString(randomInt()));
        user.setId(randomInt());
        return user;
    }

    public static Schedule generateSchedule() {
        final User user = generateUser();
        final Schedule schedule = new Schedule();
        schedule.setClassroom(new Classroom());
        schedule.setId(randomInt());
        schedule.setSubject(new Subject());
        schedule.setOwner(user.getId());
        return schedule;
    }

    public static Classroom generateClassroom() {
        final Classroom classroom = new Classroom();
        classroom.setNumber(randomInt());
        return classroom;
    }

    public static Subject generateSubject() {
        final Subject subject = new Subject();
        subject.setCapacity(randomInt());
        subject.setTitle("testTitle");
        return subject;
    }

    public static Teacher generateTeacher() {
        final Teacher teacher = new Teacher();
        teacher.setName("Name" + randomInt());
        teacher.setSurname("Surname" + randomInt());
        teacher.setUsername("username@kbss.felk.cvut.cz");
        teacher.setPassword(Integer.toString(randomInt()));
        teacher.setId(randomInt());
        return teacher;
    }

}
