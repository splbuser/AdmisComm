package com.splb.controller.listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.ArrayList;

/**
 * listener for current on-line user counter
 */
@WebListener
public class UserListener implements HttpSessionListener {

    private static final Logger log = LogManager.getLogger(UserListener.class);
    public static final String COUNTER = "user_counter";
    private final List<String> sessions = new ArrayList<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        log.info("UserListener init");
        HttpSession session = event.getSession();
        sessions.add(session.getId());
        session.setAttribute(COUNTER, getActiveSessionNumber());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        sessions.remove(session.getId());
        session.setAttribute(COUNTER, getActiveSessionNumber());
    }

    public int getActiveSessionNumber() {
        return sessions.size();
    }
}
