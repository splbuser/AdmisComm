package com.splb.service;

import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.Faculty;
import com.splb.service.exceptions.UserServiceException;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import com.splb.service.utils.notifier.exception.MailSenderException;

import java.util.List;


public class ApplicantService extends Service {


    public boolean add(Applicant applicant) throws UserServiceException {
        try {
            return udao.addApplicant(applicant);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public Applicant get(int id) throws UserServiceException {
        try {
            return udao.getApplicantById(id);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean check(String name) throws UserServiceException {
        try {
            return udao.checkApplicant(name);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public  List<Applicant> search(String name) throws UserServiceException {
        try {
            return udao.getApplicantForSearch(name);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public Applicant login(String username, String password) throws UserServiceException {
        try {
            return udao.getUser(username, password);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean block(int userId) throws UserServiceException {
        try {
            return udao.blockUserById(userId);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean unblock(int userId) throws UserServiceException {
        try {
            return udao.unblockUserById(userId);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public boolean upload(int userId, String filename) throws UserServiceException{
        try {
            return udao.upload(userId, filename);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public int length() throws UserServiceException {
        try {
            return udao.getLength();
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Applicant> getAll(int limit, int offset) throws UserServiceException{
        try {
            return udao.findAllApplicants(limit, offset);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Applicant> getNotEnroll () throws UserServiceException{
        try {
            return udao.getNotEnrollApplicants();
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public List<Faculty> getCustomList(int id) throws UserServiceException {
        try {
            return udao.getApplicantsFacultyList(id);
        } catch (UserDAOException e) {
            log.error(e.getMessage());
            throw new UserServiceException(e.getMessage());
        }
    }

    public int getPage(int limit, int listLength) {
       return  (int) Math.ceil(listLength * 1.0 / limit);
    }

    public void notifyUsers() throws UserServiceException {
        log.info("All users were notified by email");
        Sender s;
        try {
            List<Applicant> list = udao.findAllApplicants(udao.getLength(), 0);
            for (Applicant a : list
            ) {
                s = new MailSender(a.getEmail(), MailText.ENROLL_SUBJ.getText(),
                        a.getEnrollStatus() == 2 ?
                                MailText.ENROLL_BODY.getText() :
                                a.getEnrollStatus() == 1 ?
                                        MailText.ENROLL_BODY.getText() :
                                        MailText.NO_ENROLL_BODY.getText());
                s.send();
            }
        } catch (UserDAOException | MailSenderException e) {
            log.error(e.getMessage());
            throw new UserServiceException("could not notify users" + e.getMessage());
        }
    }

}
