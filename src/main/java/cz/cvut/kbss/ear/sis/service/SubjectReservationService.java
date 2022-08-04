package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.ClassroomDao;
import cz.cvut.kbss.ear.sis.dao.SubjectDao;
import cz.cvut.kbss.ear.sis.dao.SubjectReservationDao;
import cz.cvut.kbss.ear.sis.dao.UserDao;
import cz.cvut.kbss.ear.sis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SubjectReservationService {

    private final SubjectDao subjectDao;
    private final UserDao userDao;
    private final ClassroomDao classroomDao;
    private final SubjectReservationDao subjectReservationDao;

    @Autowired
    public SubjectReservationService(SubjectDao subjectDao, UserDao userDao, ClassroomDao classroomDao, SubjectReservationDao subjectReservationDao) {
        this.subjectDao = subjectDao;
        this.userDao = userDao;
        this.classroomDao = classroomDao;
        this.subjectReservationDao = subjectReservationDao;
    }

    @Transactional
    public SubjectReservation createReservation(Classroom classroom, Subject subject, Student student) {
        Objects.requireNonNull(classroom);
        Objects.requireNonNull(student);
        Objects.requireNonNull(subject);
        SubjectReservation subjectReservation = new SubjectReservation();
        if (student.getRole() == Role.STUDENT) {
            subjectReservation.setStudent(student);
            subjectReservation.setSubject(subject);
            subjectReservation.setClassroom(classroom);
        }
        subjectReservationDao.persist(subjectReservation);
        return subjectReservation;
    }

    @Transactional
    public SubjectReservation find(Integer id) {
        return subjectReservationDao.find(id);
    }

    @Transactional
    public void persist(SubjectReservation subjectReservation) {
        Objects.requireNonNull(subjectReservation);
        subjectReservationDao.persist(subjectReservation);
    }

    @Transactional
    public List<SubjectReservation> getReservationsByOwner(Integer student) {
        Objects.requireNonNull(student);
        return subjectReservationDao.findByOwner(student);
    }

    @Transactional
    public void update(SubjectReservation subRes) {
        Objects.requireNonNull(subRes);
        subjectReservationDao.update(subRes);
    }

    @Transactional
    public void remove(SubjectReservation subjectReservation) {
        Objects.requireNonNull(subjectReservation);
        subjectReservationDao.remove(subjectReservation);
    }

    @Transactional
    public void remove(List<SubjectReservation> subjectReservations) {
        Objects.requireNonNull(subjectReservations);
        for (int i = 0; subjectReservations.size() > i; i++) {
            subjectReservationDao.remove(subjectReservations.get(i));
        }
    }
}
