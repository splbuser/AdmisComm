package com.splb.service.utils.notifier;

import com.splb.service.utils.notifier.exception.SenderException;

@FunctionalInterface
public interface Sender {
    void send() throws SenderException;

}
