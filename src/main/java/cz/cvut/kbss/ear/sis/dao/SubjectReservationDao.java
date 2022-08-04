package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.dao.BaseDao;
import cz.cvut.kbss.ear.sis.model.Schedule;
import cz.cvut.kbss.ear.sis.model.SubjectReservation;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class SubjectReservationDao extends BaseDao<SubjectReservation> {
    public SubjectReservationDao() {
        super(SubjectReservation.class);
    }

    public List<SubjectReservation> findByOwner(Integer student) {
        try {
            return em.createNamedQuery("SubjectReservation.findByOwner", SubjectReservation.class)
                    .setParameter("student", student)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
