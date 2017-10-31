package li.koly;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

/**
 * message
 *
 * @author koly
 * @date 17-10-30
 */
public class BlogDataSourceFactory {
    public static DataSource getBlogDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1/test?" +
                "user=root&password=mysqlHolder");
        return dataSource;
    }
}
