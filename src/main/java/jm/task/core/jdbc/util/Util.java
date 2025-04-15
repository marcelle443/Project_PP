package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// реализуйте настройку соеденения с БД
public class Util { //мой коммент * класс Util - создаёт соединение с БД, использует Драйвер

    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private final static String USERNAME = "mars";
    private final static String PASSWORD = "gordon44free.";

        public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            //System.out.println("Соединение установлено!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
            System.out.println("Ошибка соединения!");
        }
        return connection;
    }
}

