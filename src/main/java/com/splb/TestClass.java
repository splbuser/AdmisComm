package com.splb;

import com.splb.model.dao.UserDAO;
import com.splb.model.dao.connection.ConnectionBuilder;
import com.splb.model.dao.connection.PoolConnectionBuilder;
import com.splb.model.dao.exception.DAOException;
import com.splb.model.dao.exception.FacultyDAOException;
import com.splb.model.dao.exception.StatementDAOException;
import com.splb.model.dao.exception.UserDAOException;
import com.splb.model.dao.implementation.*;
import com.splb.model.entity.Applicant;
import com.splb.model.entity.EnrollStatus;
import com.splb.model.entity.Faculty;
import com.splb.model.entity.Statement;
import com.splb.service.ApplicantService;
import com.splb.service.sorting.SortStatementImpl;
import com.splb.service.utils.DataValidator;
import com.splb.service.utils.notifier.MailSender;
import com.splb.service.utils.notifier.MailText;
import com.splb.service.utils.notifier.Sender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass {

    public static final Logger log = LogManager.getLogger(TestClass.class);


    public static void main(String[] args) throws Exception {

        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        ApplicantService srv = new ApplicantService();
        FacultyDAOImpl facultyDAO;
        facultyDAO = FacultyDAOImpl.getInstance();
//        StatementDAOImpl statementDAO;
//        statementDAO = StatementDAOImpl.getInstance();
//        ApplicantResultDAOImpl applicantResultDAO;
//        applicantResultDAO = ApplicantResultDAOImpl.getInstance();
//        EnrollmentDAOImpl enrollmentDAO;
//        enrollmentDAO = EnrollmentDAOImpl.getInstance();

//        System.out.println(userDAO.getApplicantById(10));
        System.out.println(facultyDAO.deleteFacultyByID(2));


//        try {
//            SortStatementImpl list = new SortStatementImpl();
//            List<Statement> statementList = list.getSortedList("byTotalScore", "DSC", statementDAO.getStatementList());
//            log.info("List size {}", statementList.size());
//            for (Statement s : statementList
//            ) {
//                int userId = s.getApplicant().getId();
//                int facultyId = s.getFaculty().getId();
//                int budget = s.getFaculty().getBudgetPlaces();
//                int total = s.getFaculty().getTotalPlaces();
//                if (budget > 0) {
//                    enrollmentDAO.add(facultyId, userId, 2);
//                    log.info("{} applicant added to {} faculty for BUDGET", s.getApplicant().getLastName(),
//                            s.getFaculty().getName());
//                    budget--; total--;
//                    s.getFaculty().setBudgetPlaces(budget);
//                    s.getFaculty().setTotalPlaces(total);
//                } else if (budget == 0 && total > 0) {
//                    enrollmentDAO.add(facultyId, userId, 1);
//                    log.info("{} applicant added to {} faculty for CONTRACT", s.getApplicant().getLastName(),
//                            s.getFaculty().getName());
//                    total--;
//                    s.getFaculty().setTotalPlaces(total);
//                } else if (budget ==0 && total ==0) {
//                    s.getApplicant().setEnrollStatus(0);
//                }
//                // one more if whe places out of count
//                // send mail
//            }
//        } catch (DAOException e) {
//           log.error(e.getMessage());
//        }
//**************************************************************************************
//        Sender s = new MailSender("therealant@gmail.com", MailText.ENROLL_SUBJ,
//                String.format((MailText.ENROLL_BODY), "contract", "Serious") );
//        s.send();
//**************************************************************************************

//        UserDAOImpl userDAOImpl = UserDAOImpl.getInstance();
//        EnrollmentDAOImpl enrollmentDAOImpl = EnrollmentDAOImpl.getInstance();
//        List<Applicant> list = userDAOImpl.findAllApplicants(10, 1);

//        for (Applicant a : list) {log.info(a.getResult());}

        // сортированый список по рейтингу апликантов
//        list.sort(Comparator.comparing(Applicant::getResult).reversed());
//        log.info(list);
//        log.info(list.get(0));

        // список факультетов топового аппликанта
//        List<Faculty> faculties = userDAOImpl.getApplicantsFacultyList(list.get(0).getId());
//        log.info(faculties);
//        enrollmentDAO.add(2, 10, 1);

//        log.info(EnrollStatus.FOR_BUDGET);

//        try {
//            StatementDAOImpl.getInstance().getStatementList();
//            } catch (StatementDAOException ex) {
//            throw new RuntimeException(ex);
//        }


//        System.out.println("================ VALIDATION =====================");
//        String nullTest = null;
//        String name = "SOME NAME";
//        String password = "Stream@Java8";
//        System.out.println(name + DataValidator.validateName(name));
//        System.out.println(password + DataValidator.validatePassword(password));
//
//        System.out.println("na_Gi948_BATOR! " + DataValidator.validateUserName("na_Gi948_BATOR!"));
//
//        System.out.println("NULL " + DataValidator.validateUserName(nullTest));
//        System.out.println("NULL " + DataValidator.validateName(nullTest));
//        System.out.println("NULL " + DataValidator.validatePassword(nullTest));
//        System.out.println("NULL email " + DataValidator.validateEmail(nullTest));


//        System.out.println(userDAO.getApplicantsFacultyList(2));
//        System.out.println(userDAO.getApplicantById(1).isAdminStatus());
//
//
//        for (int i = 0; i < 10; i++) {
//            System.out.println(userDAO.getApplicantById(i).isAdminStatus());
//        }

//        Applicant testApl = new Applicant();
//        testApl.setUserName("TestUserName");
//        testApl.setPassword(PasswordEncrypt.SimpleEncode("123qwerty"));
//        testApl.setFirstName("Vasily");
//        testApl.setLastName("Mesyatz");
//        testApl.setEmail("testuser@e.mail");
//        testApl.setCity("AnyCity");
//        testApl.setRegion("SomeRegion");
//        testApl.setEducationalInstitution("NoEducation!");
//        System.out.println("User has been added: " + userDAO.addApplicant(testApl));
//
//        userDAO.getApplicantById(3);
//        System.out.println("User TestUserName has ID ==> " + userDAO.findApplicantByName("El_Diablo"));
//        System.out.println("User has been deleted: " + userDAO.deleteApplicantByLogin("abby555"));

//        for (int i = 0; i < 20; i++) {
//            System.out.println("User #" + i + " block status: " + userDAO.isBlockedUserCheck(i));
//        }
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println(userDAO.blockUserById(i));
//        }
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println("User #" + i + " block status: " + userDAO.isBlockedUserCheck(i));
//        }
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println(userDAO.unBlockUserById(i));
//        }
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println("User #" + i + " block status: " + userDAO.isBlockedUserCheck(i));
//        }


//        System.out.println(facultyDAO.getSum(2,2) == 19);
//        System.out.println("Faculty TestFaculty has ID ==> " + facultyDAO.checkFacultyByName("Biology"));
//        System.out.println("Faculty #1 is Exist by ID ==> " + facultyDAO.checkFacultyById(1));
//        System.out.println("Faculty #8 is Exist by ID ==> " + facultyDAO.checkFacultyById(8));
//        System.out.println("Faculty Biology is Exist by name ==> " + facultyDAO.checkFacultyByName("Biology"));
//        System.out.println("Faculty ANY_FACULTY is Exist by name ==> " + facultyDAO.checkFacultyByName("sfhfghdfgh"));
//        Faculty facultyOne = new Faculty();
//        facultyOne.setName("TestFaculty");
//        facultyOne.setBudgetPlaces(1);
//        facultyOne.setTotalPlaces(10);
//        System.out.println("Faculty has been added: " + facultyDAO.addFaculty(facultyOne));
//
//        System.out.println("getApplicantsForFaculty #3 ==> " + facultyDAO.getApplicantsForFaculty(3));
////        System.out.println(facultyDAO.findAllFaculty().toString());
//
//        System.out.println("Faculty TestFaculty has ID ==> " + facultyDAO.checkFacultyByName("Biology"));
//        System.out.println("Faculty has been deleted: " + facultyDAO.deleteFacultyByName("Economics"));
//
//        System.out.println("Result of e-mail validation: " + EmailValidator.validate("anto123ni@gmail.com"));
//
//        ApplicantResult appRes = new ApplicantResult(10, 5, 6, 9, 5, 12, 9, 7, 3,8);
//        testApl.setApplicantResult(appRes);
//        System.out.println("ApplicantResult ==> " + appRes);
//        for (int i = -2; i < 20; i++) {
//            StatementDAO statementDAO =StatementDAO.getInstance();
//            System.out.println(1 + "+"+ i + " = " + statementDAO.checkUserFaculty(1, i));
//        }

//        List<Applicant> list = StatementDAO.getInstance().getFacultysApplicantsFromStatement(1);
//        System.out.println(list.toString());


//
//
//        System.out.println(PasswordEncrypt.SimpleEncode("128500"));
//        System.out.println(PasswordEncrypt.SimpleDecode("cXdlcnQ="));
//
//        PasswordEncrypt pe = new PasswordEncrypt();
//        try {
//            byte[] salt = pe.generateSalt();
//            byte[] encrypted = pe.getEncryptedPassword("128500", salt);
//            System.out.println(new String(encrypted));
//            System.out.println(pe.authenticate("128500", encrypted, salt));
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            throw new RuntimeException(e);
//        }

//        StatementDAO statementDAO = StatementDAO.getInstance();
//        System.out.println(statementDAO.addUserToFaculty(1, 2));
    }

//    protected void generateReport(Order order) {
//
//        final String username = "your_email";
//        final String password = "crypto_password";
//        String recipientEmail = "receiver_email";
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "587");
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true"); //TLS
//
//        Session session = Session.getInstance(prop,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//        try {
//            //construct the pdf body part
//            DataSource dataSource = new ByteArrayDataSource(bytePdfFile(order), "application/pdf");
//            MimeBodyPart pdfBodyPart = new MimeBodyPart();
//            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
//
//            //construct the mime multi part
//            MimeMultipart mimeMultipart = new MimeMultipart();
//            mimeMultipart.addBodyPart(pdfBodyPart);
//            pdfBodyPart.setFileName("invoice.pdf");
//
//            //create the sender/recipient addresses
//            InternetAddress iaSender = new InternetAddress(username);
//            InternetAddress iaRecipient = new InternetAddress(recipientEmail);
//
//            //construct the mime message
//            MimeMessage mimeMessage = new MimeMessage(session);
//            mimeMessage.setSender(iaSender);
//            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
//            mimeMessage.setContent(mimeMultipart);
//
//            //send off the email
//            Transport.send(mimeMessage);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }


}
