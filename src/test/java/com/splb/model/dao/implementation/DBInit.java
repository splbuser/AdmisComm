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

    public static void startUp() throws Exception {
        URL url1 = UserDAOImplTest.class.getClassLoader()
                .getResource("adm_comm_db.sql");
        URL url2 = UserDAOImplTest.class.getClassLoader()
                .getResource("adm_comm_data.sql");

        List<String> str1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = String.join("", str1);

        List<String> str2 = Files.readAllLines(Paths.get(url2.toURI()));
        String sql2 = String.join("", str2);

        try (Connection con = new DirectConnectionBuilder().getConnection();
             Statement stmt = con.createStatement();
        ) {
            stmt.execute(sql1);
            stmt.execute(sql2);
        }
    }
}

