package cz.cvut.kbss.ear.sis.dao;

import cz.cvut.kbss.ear.sis.model.Admin;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao extends BaseDao<Admin> {
    public AdminDao() {
        super(Admin.class);
    }
}
