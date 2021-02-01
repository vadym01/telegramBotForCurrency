package com.example.demo.repositories;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//@ContextConfiguration("/applicationContext.xml")
public class DatabaseConnection {


    private static String url = "jdbc:postgresql://ec2-34-253-148-186.eu-west-1.compute.amazonaws.com/dec1tvkcksnh6p";

    private static String userName = "xrigpeizxrgzgd";

    private static String password = "014c157a58038f6a083df95f836401341a2588921a1132e680ddd1cd956e6e56";

    private static String driver = "org.postgresql.Driver";

    private static Connection con = null;

    static
    {

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userName, password);
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return con;
    }
}
