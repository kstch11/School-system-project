package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.dao.BaseDao;
import cz.cvut.kbss.ear.sis.model.Subject;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDao extends BaseDao<Subject> {
    public SubjectDao() {
        super(Subject.class);
    }


}
