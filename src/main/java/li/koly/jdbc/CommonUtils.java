package li.koly.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonUtils {
    public static void printResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1) + "["
                    + resultSet.getString(2) + "]"
                    + resultSet.getString(3) + "--"
                    + resultSet.getString("author") + "=="
                    + resultSet.getDate("create_time"));
        }
    }
}
