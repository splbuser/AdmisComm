package com.splb.model.dao.implementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class DBInit {

    private static final Logger log = LogManager.getLogger(DBInit.class);

    public static void startUp(Class<?> tClass) throws Exception {
        URL url1 = tClass.getClassLoader()
                .getResource("test_db.sql");

        List<String> str1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = String.join("", str1);
        try (Connection con = new DirectConnectionBuilder().getConnection();
             Statement stmt = con.createStatement()
        ) {
            stmt.execute(sql1);
        }
    }
}

