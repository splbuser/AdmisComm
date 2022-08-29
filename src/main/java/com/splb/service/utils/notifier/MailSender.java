package com.splb.service.utils.notifier;

import com.splb.service.utils.PassCrypt;
import com.splb.service.utils.notifier.exception.MailSenderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

public class MailSender implements Sender {

    private static final Logger log = LogManager.getLogger(MailSender.class);
    private String username;
    private String password;

    private final String recipient;
    private final String subj;
    private final String msg;

    public MailSender(String recipient, String subj, String msg) {
        this.recipient = recipient;
        this.subj = subj;
        this.msg = msg;
    }

    @Override
    public void send() throws MailSenderException {

        String host;
        String port;
        String auth;
        String tls;
        try {
            username = MailConfig.getProperty(MailConfig.USER);
            String passwordHash = MailConfig.getProperty(MailConfig.PASSWORD);
            password = new String(PassCrypt.decode(passwordHash));
            host = MailConfig.getProperty(MailConfig.HOST);
            port = MailConfig.getProperty(MailConfig.PORT);
            auth = MailConfig.getProperty(MailConfig.AUTH);
            tls = MailConfig.getProperty(MailConfig.TLS);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new MailSenderException(e.getMessage());
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", auth);
        prop.put("mail.smtp.starttls.enable", tls);

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("committee@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subj);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            log.info("e-mail sent to {}", recipient);

        } catch (MessagingException e) {
            log.error("could not send mail: {}", e.getMessage());
            throw new MailSenderException(e.getMessage());
        }
    }
}
