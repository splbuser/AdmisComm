package com.splb.model.dao.implementation;

import com.splb.model.dao.AbstractDAO;
import com.splb.model.dao.RegisterDAO;
import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.SQLQuery;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.RegisterDAOException;
import com.splb.model.entity.Applicant;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDAOImpl extends AbstractDAO implements RegisterDAO {

    private static RegisterDAOImpl registerDAOImpl;

    private RegisterDAOImpl() {
        setConnectionBuilder(new PoolConnectionBuilder());
//        setConnectionBuilder(new DirectConnectionBuilder());
        log = LogManager.getLogger(getClass().getName());
    }

    public static synchronized RegisterDAOImpl getInstance() {
        if (registerDAOImpl == null) {
            registerDAOImpl = new RegisterDAOImpl();
        }
        return registerDAOImpl;
    }

    @Override
    public boolean insert(Applicant applicant) throws RegisterDAOException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (
                    PreparedStatement ps = conn.prepareStatement(SQLQuery.CREATE_USER);
            ) {
                ps.setString(1, applicant.getUserName());
                ps.setString(2, applicant.getPassword());
                ps.setString(3, applicant.getFirstName());
                ps.setString(4, applicant.getLastName());
                ps.setString(5, applicant.getEmail());
                ps.setString(6, applicant.getCity());
                ps.setString(7, applicant.getRegion());
                ps.setString(8, applicant.getEducationalInstitution());
                ps.executeUpdate();
                conn.commit();
                log.info("Registration was successful");
                return true;
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new RegisterDAOException(e.getMessage());
            } finally {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RegisterDAOException("could not add new user: " + e.getMessage());

        }
    }
}