package li.koly;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import sun.misc.Unsafe;

import javax.sql.DataSource;

/**
 * Application
 *
 * @author koly
 * @date 17-10-30
 */
public class App {

    public static void main(String[] args) {
        DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Blog blog = session.selectOne("li.koly.BlogMapper.selectBlog", 1L);
            System.out.println(blog.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

}
