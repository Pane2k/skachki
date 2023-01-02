package by.nevyhlas.skachki;

import java.io.IOException;
import java.sql.*;

public class DataBase {
    public static String NAME_USER = "root";
    public static String PASSWORD = "1234";
    public static String URL = "jdbc:mysql://localhost:3306/skachki";
    public static String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static Connection connection;
    public static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(URL, NAME_USER, PASSWORD);
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("Connection failed");
        }
    }
    static {
        try {
            statement = connection.createStatement();
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("Statement failed");
        }
    }


}
