package com.splb.service;


import com.splb.model.dao.connection.DirectConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.constant.Fields;
import com.splb.model.dao.exception.DAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Enrollment;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.EnrollmentServiceException;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import com.splb.service.utils.notifier.exception.SenderException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ApplicantService extends Service {

    public ApplicantService() {
        setConnectionBuilder(new PoolConnectionBuilder());
    }

    public ApplicantService(DirectConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    public boolean add(Applicant applicant) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.addApplicant(applicant, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public Applicant get(int id) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getApplicantById(id, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean check(String name) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.checkApplicant(name, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Applicant> search(String name) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getApplicantForSearch(name, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public Applicant login(String username, String password) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getUser(username, password, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean block(int userId) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.blockUserById(userId, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean unblock(int userId) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.unblockUserById(userId, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean upload(int userId, String filename) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.upload(userId, filename, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public int length() throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getLength(con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Applicant> getAll(int limit, int offset) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.findAllApplicants(limit, offset, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Applicant> getNotEnroll() throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getNotEnrollApplicants(con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Faculty> getCustomList(int id) throws UserServiceException {
        try (Connection con = getConnection()) {
            return udao.getApplicantsFacultyList(id, con);
        } catch (UserDAOException | SQLException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public int getPage(int limit, int listLength) {
        return (int) Math.ceil(listLength * 1.0 / limit);
    }

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
                if (status == 1 || status == 2) {
                    Enrollment enrollment = edao.getApplicantEnrollStatus(a.getId(), con);
                    String faculty = enrollment.getFaculty().getName();
                    log.info("STEP 3");
                    switch (status) {
                        case (1) -> body = String.format(MailText.ENROLL_BODY.getText(), Fields.BUDGET, faculty);
                        case (2) -> body = String.format(MailText.ENROLL_BODY.getText(), Fields.CONTRACT, faculty);
                        default -> body = MailText.DEFAULT.getText();
                    }
                } else if (status == 0) {
                    body = MailText.NO_ENROLL_BODY.getText();
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