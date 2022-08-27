package com.splb.service;

import com.splb.model.dao.UserDAO;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.dao.implementation.UserDAOImpl;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.FacultyServiceException;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import com.splb.service.utils.notifier.exception.MailSenderException;

import java.util.List;


public class ApplicantService extends Service {

    UserDAO dao = UserDAOImpl.getInstance();

    public boolean add(Applicant applicant) throws UserServiceException {
        try {
            return dao.addApplicant(applicant);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public Applicant get(int id) throws UserServiceException {
        try {
            return dao.getApplicantById(id);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }


    public Applicant login(String username, String password) throws UserServiceException {
        try {
            return dao.getUser(username, password);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean block(int userId) throws UserServiceException {
        try {
            return dao.blockUserById(userId);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean unblock(int userId) throws UserServiceException {
        try {
            return dao.unblockUserById(userId);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public int length() throws UserServiceException {
        try {
            return dao.getLength();
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Applicant> getAll(int limit, int offset) throws UserServiceException{
        try {
            return dao.findAllApplicants(limit, offset);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Faculty> getCustomList(int id) throws UserServiceException {
        try {
            return dao.getApplicantsFacultyList(id);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public void notifyUsers() throws UserServiceException {
        log.info("All users were notified by email");
        Sender s;
        try {
            List<Applicant> list = dao.findAllApplicants(dao.getLength(), 0);
            for (Applicant a : list
            ) {
                s = new MailSender(a.getEmail(), MailText.ENROLL_SUBJ,
                        a.getEnrollStatus() == 2 ?
                                MailText.ENROLL_BODY :
                                a.getEnrollStatus() == 1 ?
                                        MailText.ENROLL_BODY :
                                        MailText.NO_ENROLL_BODY);
                s.send();
            }
        } catch (UserDAOException | MailSenderException e) {
            log.error(e.getMessage());
            throw new UserServiceException("could not notify users" + e.getMessage());
        }
    }

}
