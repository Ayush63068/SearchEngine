package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
     static Connection connection=null;

    public static Connection getConnection() {
        if(connection!=null){
            return connection;
        }
            String user="root";
            String pwd="Ayush@2205";
            String db="searchengineapp";
            return getConnection(user,pwd,db);
    }
    private static Connection getConnection(String user,String pwd,String db){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
