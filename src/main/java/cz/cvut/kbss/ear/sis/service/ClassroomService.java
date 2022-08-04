package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.ClassroomDao;
import cz.cvut.kbss.ear.sis.model.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ClassroomService {
    private final ClassroomDao classroomDao;

    @Autowired
    public ClassroomService(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }

    @Transactional
    public void persist(Classroom classroom) {
        Objects.requireNonNull(classroom);
        classroomDao.persist(classroom);
    }

    @Transactional
    public Classroom find(Integer number) {
        Objects.requireNonNull(number);
        return classroomDao.findByNumber(number);
    }
}
