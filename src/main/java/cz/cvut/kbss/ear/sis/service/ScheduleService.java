package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.ScheduleDao;
import cz.cvut.kbss.ear.sis.dao.SubjectDao;
import cz.cvut.kbss.ear.sis.dao.TeacherDao;
import cz.cvut.kbss.ear.sis.dao.UserDao;
import cz.cvut.kbss.ear.sis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ScheduleService {

    private final ScheduleDao scheduleDao;
    private final TeacherDao teacherDao;
    private final SubjectDao subjectDao;
    private final UserDao userDao;
    private final Random random = new Random();


    @Autowired
    public ScheduleService(ScheduleDao scheduleDao, TeacherDao teacherDao, SubjectDao subjectDao, UserDao userDao) {
        this.scheduleDao = scheduleDao;
        this.teacherDao = teacherDao;
        this.subjectDao = subjectDao;
        this.userDao = userDao;
    }

    @Transactional
    public List<Schedule> getScheduleByOwner(Integer owner) {
        Objects.requireNonNull(owner);
        return scheduleDao.findByOwner(owner);
    }

    @Transactional
    public Schedule createScheduleByTeacher(Teacher teacher, Subject subject, Classroom classroom, String time) {
        Objects.requireNonNull(teacher);
        Objects.requireNonNull(subject);
        Objects.requireNonNull(classroom);
        Objects.requireNonNull(time);
        Schedule schedule = new Schedule();
        schedule.setSubject(subject);
        schedule.setClassroom(classroom);
        //schedule.setId(random.nextInt());
        schedule.setOwner(teacher.getId());
        schedule.setTime(time);
        scheduleDao.persist(schedule);
        return schedule;
    }

    @Transactional
    public Schedule createScheduleByStudent(SubjectReservation subjectReservation, Integer teacherId) {
        Objects.requireNonNull(subjectReservation);
        Objects.requireNonNull(teacherId);
        Schedule schedule = new Schedule();

        List<Schedule> teacherSchedule = findAll(userDao.find(teacherId));
        for (int i = 0; teacherSchedule.size() > i; i++) {
            if (teacherSchedule.get(i).getSubject().getTitle().equals(subjectReservation.getSubject().getTitle())) {
                schedule.setTime(teacherSchedule.get(i).getTime());
            }
        }
        schedule.setSubject(subjectReservation.getSubject());
        schedule.setClassroom(subjectReservation.getClassroom());
        schedule.setOwner(subjectReservation.getStudent().getId());
        //schedule.setTime(subjectReservation.getSubject().getTitle());
        //schedule.setId(random.nextInt());
        scheduleDao.persist(schedule);
        return schedule;
    }

    @Transactional
    public void persist(Schedule schedule) {
        Objects.requireNonNull(schedule);
        scheduleDao.persist(schedule);
    }

    @Transactional
    public List<Schedule> findAll(User user) {
        Objects.requireNonNull(user);
        return scheduleDao.findByOwner(user.getId());
    }

    @Transactional
    public void remove(Schedule schedule) {
        Objects.requireNonNull(schedule);
        scheduleDao.remove(schedule);
    }

    @Transactional
    public void remove(List<Schedule> schedules) {
        Objects.requireNonNull(schedules);
        for (int i = 0; schedules.size() > i; i++) {
            scheduleDao.remove(schedules.get(i));
        }
    }

}
