package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.dao.BaseDao;
import cz.cvut.kbss.ear.sis.model.Student;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDao<Student> {
    public StudentDao() {
        super(Student.class);
    }


}
