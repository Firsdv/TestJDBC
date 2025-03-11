package jm.task.core.jdbc.util;

import java.sql.*;


public class Util {
   private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";// реализуйте настройку соеденения с БД            написал 4 поля
   private static final String DB_URL = "jdbc:mysql://localhost:3306/dbuser?serverTimezone=UTC";
   private static final String DB_USERNAME = "root";
   private static final String DB_PASSWORD = "roott";


   public static Connection getConnection() {
      Connection connection = null;
       try {
           Class.forName(DB_DRIVER);
           connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
          System.out.println("Connection OK");
       } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
          System.out.println("Connection ERROR");
       }
       return connection;
   }
}
