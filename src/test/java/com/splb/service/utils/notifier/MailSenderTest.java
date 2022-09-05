package com.splb.service.utils.notifier;

import com.splb.service.utils.notifier.exception.SenderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MailSenderTest {

    @Test
    void send() throws SenderException {
        Sender s = new MailSender("therealant@gmail.com", MailText.ENROLL_SUBJ.getText(),
                String.format((MailText.ENROLL_BODY.getText()), "contract", "Serious"));
        s.send();
    }
}