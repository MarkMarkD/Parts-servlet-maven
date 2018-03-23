/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.transasia.mvnproject1.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author banashko.dv
 */
public class DBConnUtils {
    public static Connection getConnection() throws ClassNotFoundException, SQLException {

//        String url = "jdbc:postgresql://127.0.0.1:5432/transasiaPartsDB";
//        String name = "postgres";
//        String pass = "root";
//        Class.forName("org.postgresql.Driver");
//        Connection conn = DriverManager.getConnection(url, name, pass);

        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
        return conn;
       
    }

    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
