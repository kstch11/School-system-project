package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.dao.BaseDao;
import cz.cvut.kbss.ear.sis.model.Schedule;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class ScheduleDao extends BaseDao<Schedule> {
    public ScheduleDao() {
        super(Schedule.class);
    }

    public List<Schedule> findByOwner(Integer owner) {
        try {
            return em.createNamedQuery("Schedule.findByOwner", Schedule.class)
                    .setParameter("owner", owner)
                    .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
