package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.UserDao;
import cinema.exception.DataProcessingException;
import cinema.model.User;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(SessionFactory factory) {
        super(factory, User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.info("Method findByEmail was called with email " + email);
        try (Session session = factory.openSession()) {
            Query<User> findByEmail = session.createQuery(
                            "FROM User u "
                                    + "JOIN FETCH u.roles "
                                    + "WHERE email = :email", User.class);
            findByEmail.setParameter("email", email);
            return findByEmail.uniqueResultOptional();
        } catch (Exception e) {
            logger.error("Can't find user by email " + email);
            throw new DataProcessingException("User with email " + email + " not found", e);
        }
    }
}
