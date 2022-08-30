package com.splb.service.utils.notifier;

import com.splb.service.utils.notifier.exception.MailSenderException;

public interface Sender {
   void send() throws MailSenderException;

}
