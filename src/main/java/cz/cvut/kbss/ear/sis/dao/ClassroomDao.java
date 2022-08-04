package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.model.Classroom;
import cz.cvut.kbss.ear.sis.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class ClassroomDao extends BaseDao<Classroom> {
    public ClassroomDao() {
        super(Classroom.class);
    }

    public Classroom findByNumber(Integer number) {
        try {
            return em.createNamedQuery("Classroom.findByNumber", Classroom.class)
                    .setParameter("number", number)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
