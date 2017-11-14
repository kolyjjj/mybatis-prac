package li.koly.jdbc;

import java.sql.*;

public class DriverManagerPrac {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC", "koly", "koly");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from blog");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "["
                        + resultSet.getString(2) + "]"
                        + resultSet.getString(3) + "--"
                        + resultSet.getString("author") + "=="
                        + resultSet.getDate("create_time"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
