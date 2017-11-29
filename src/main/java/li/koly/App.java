package li.koly;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Application
 *
 * @author koly
 * @date 17-10-30
 */
public class App {

    public static void main(String[] args) {
//        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSessionFactory sqlSessionFactory = xmlSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Blog blog = session.selectOne("li.koly.BlogMapper.selectBlog", 1L);
//            BlogMapper mapper = session.getMapper(BlogMapper.class);
//            Blog blog = mapper.selectBlog(1L);
            System.out.println(blog.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static SqlSessionFactory getSqlSessionFactory() {
        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static SqlSessionFactory xmlSqlSessionFactory() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);

    }

}
