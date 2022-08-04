package cz.cvut.kbss.ear.sis.service;

import cz.cvut.kbss.ear.sis.dao.SubjectDao;
import cz.cvut.kbss.ear.sis.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SubjectService {

    private final SubjectDao subjectDao;

    @Autowired
    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Transactional
    public Subject find(Integer id) {
        return subjectDao.find(id);
    }

    @Transactional
    public List<Subject> findAll() {
        return subjectDao.findAll();
    }

    @Transactional
    public void persist(Subject subject) {
        Objects.requireNonNull(subject);
        subjectDao.persist(subject);
    }
}
