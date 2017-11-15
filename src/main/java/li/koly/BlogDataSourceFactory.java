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
        dataSource.setURL(Constants.DATABASE_URL);
        dataSource.setUser(Constants.DATABASE_USER_NAME);
        dataSource.setPassword(Constants.DATABASE_PASSWORD);
        return dataSource;
    }
}
