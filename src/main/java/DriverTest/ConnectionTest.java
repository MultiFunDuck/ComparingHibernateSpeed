package DriverTest;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:postgresql://localhost:8080/testdb" +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&serverTimezone=UTC";
        String user = "postgres";
        String password = "password";
        System.out.println(jdbcUrl);
        try {

            System.out.println("Connecting to dataBase" + jdbcUrl);

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, password);

            System.out.println("SuccessfulConnection!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
