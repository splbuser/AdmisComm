package com.splb.service.utils.notifier;

import com.splb.service.utils.notifier.exception.SenderException;

public interface Sender {
    void send() throws SenderException;
}
