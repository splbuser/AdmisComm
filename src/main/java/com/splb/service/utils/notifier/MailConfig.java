package com.splb.service.utils.notifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailConfig {

    public static final String USER = "username";
    public static final String PASSWORD = "private";
    public static final String HOST = "mail.smtp.host";
    public static final String PORT = "mail.smtp.port";
    public static final String AUTH = "mail.smtp.auth";
    public static final String TLS = "mail.smtp.starttls.enable";

    public static final Logger log = LogManager.getLogger(MailConfig.class);

    private static final Properties properties = new Properties();

    public static synchronized String getProperty(String name) throws IOException {
        if (properties.isEmpty()) {
            try (InputStream is = MailConfig.class.getClassLoader()
                    .getResourceAsStream("mail.properties")) {
                properties.load(is);
            } catch (IOException e) {
                log.error("could not read property: {}", e.getMessage());
                throw new IOException(e.getMessage());
            }
        }
        return properties.getProperty(name);
    }

    private MailConfig() {
        throw new IllegalStateException("Utility class");
    }
}