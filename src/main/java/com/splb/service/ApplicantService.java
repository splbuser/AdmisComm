package com.splb.service;

import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.exception.DAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Enrollment;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import com.splb.service.utils.notifier.exception.SenderException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class ApplicantService extends Service {

    /**
     * none parametrized constructor for ConnectionPool initialize
     */
    public ApplicantService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

    /**
     * parametrized constructor to create DirectConnection for testing
     *
     * @param connectionBuilder
     */
    public ApplicantService(DirectConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    /**
     * add new applicant
     *
     * @param applicant
     * @return true if applicant was added
     * @throws UserServiceException
     */
    public boolean add(Applicant applicant) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.addApplicant(applicant, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * update user info
     * @param applicant
     * @return
     * @throws UserServiceException
     */
    public boolean update(Applicant applicant) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.update(applicant, con);
        }  catch (SQLException | UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }
    /**
     * get applicant by ID
     *
     * @param id
     * @return Applicant
     * @throws UserServiceException
     */
    public Applicant get(int id) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getApplicantById(id, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * check if applicant with required name exist
     *
     * @param name
     * @return
     * @throws UserServiceException
     */
    public boolean check(String name) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.checkApplicant(name, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * check if required email exists in database
     * @param email
     * @throws UserServiceException
     */
    public boolean checkEmail(String email) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getByEmail(email, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * check username uniqueness
     * @param login
     * @return
     * @throws UserServiceException
     */
    public boolean checkUsername(String login) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.findApplicantByName(login, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * find applicant by searching parameters (username, first or last name)
     *
     * @param name
     * @return
     * @throws UserServiceException
     */
    public List<Applicant> search(String name) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getApplicantForSearch(name, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * find user in database foe login
     *
     * @param username
     * @param password
     * @return
     * @throws UserServiceException
     */
    public Applicant login(String username, String password) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getUser(username, password, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * block user by id
     *
     * @param userId
     * @return true if user was blocked
     * @throws UserServiceException
     */
    public boolean block(int userId) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.blockUserById(userId, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * unblock user by id
     *
     * @param userId
     * @return true if user was unblocked
     * @throws UserServiceException
     */
    public boolean unblock(int userId) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.unblockUserById(userId, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * add uploaded filename to userinfo
     *
     * @param userId
     * @param filename
     * @return true if success
     * @throws UserServiceException
     */
    public boolean upload(int userId, String filename) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.upload(userId, filename, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * returns user list length, exclude admin users
     *
     * @throws UserServiceException
     */
    public int length() throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getLength(con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * returns list of applicants with limit and offset for pagination
     *
     * @param limit
     * @param offset
     * @return List<Applicant>
     * @throws UserServiceException
     */
    public List<Applicant> getAll(int limit, int offset) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.findAllApplicants(limit, offset, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * returns no enrolled applicants
     *
     * @return List<Applicant>
     * @throws UserServiceException
     */
    public List<Applicant> getNotEnroll() throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getNotEnrollApplicants(con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * method returns sorted list of faculties where the applicant registered
     *
     * @param id
     * @return List<Faculty>
     * @throws UserServiceException
     */
    public List<Faculty> getCustomList(int id) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getApplicantsFacultyList(id, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * change user password on demand after verification
     * @param email
     * @param password
     * @return 1 if password was changed
     * @throws UserServiceException
     */
    public int changePassword (String email, String password) throws UserServiceException  {
        try (Connection con = getConnection()) {
            return udao.changePassword (email, password, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    /**
     * returns number of pages for pagination
     *
     * @param limit
     * @param listLength
     */
    public int getPage(int limit, int listLength) {
        return (int) Math.ceil(listLength * 1.0 / limit);
    }

    /**
     * notify all users by e-mail about enrollment results
     *
     * @throws UserServiceException
     */
    public void notifyUsers() throws UserServiceException {
        Sender s;
        try (Connection con = getConnection()) {
            int length = udao.getLength(con);
            List<Applicant> list = udao.findAllApplicants(length, 0, con);
            for (Applicant a : list
            ) {
                int status = a.getEnrollStatus();
                String email = a.getEmail();
                String body = null;
                Enrollment enrollment = edao.getApplicantEnrollStatus(a.getId(), con);
                switch (status) {
                    case (0) -> body = MailText.NO_ENROLL_BODY.getText();
                    case (1) -> body = String.format(MailText.ENROLL_BODY.getText(), Fields.CONTRACT,
                          enrollment.getFaculty().getName());
                    case (2) -> body = String.format(MailText.ENROLL_BODY.getText(), Fields.BUDGET,
                            enrollment.getFaculty().getName());
                    default -> body = MailText.DEFAULT.getText();
                }
                s = new MailSender(email, MailText.ENROLL_SUBJ.getText(), body);
                s.send();
            }
        } catch (DAOException | SenderException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException("could not notify users" + e.getMessage());
        }
    }
}