package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.dao.BaseDao;
import cz.cvut.kbss.ear.sis.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao extends BaseDao<Teacher> {
    public TeacherDao() {
        super(Teacher.class);
    }
}
