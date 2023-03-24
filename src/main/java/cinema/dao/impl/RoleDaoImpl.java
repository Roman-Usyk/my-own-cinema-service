package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    private static final Logger logger = LogManager.getLogger(RoleDaoImpl.class);

    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        logger.info("Method getByName was called with role name " + roleName);
        try (Session session = factory.openSession()) {
            Query<Role> roleQuery =
                    session.createQuery("FROM Role WHERE roleName = :roleName", Role.class);
            roleQuery.setParameter("roleName", Role.RoleName.valueOf(roleName));
            return roleQuery.uniqueResultOptional();
        } catch (Exception e) {
            logger.error("Can't get role by role name " + roleName);
            throw new DataProcessingException("Role with role name " + roleName + " not found", e);
        }
    }
}
