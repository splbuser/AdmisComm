package com.splb.service.utils.notifier;

import com.splb.service.utils.notifier.exception.SenderException;

/**
 * common interface for sending notifications
 */
public interface Sender {
    void send() throws SenderException;
}
