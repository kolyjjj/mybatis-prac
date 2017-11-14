package li.koly.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourcePrac {
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://192.168.1.10:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC");
        dataSource.setUser("koly");
        dataSource.setPassword("koly");
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from blog");
            CommonUtils.printResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
