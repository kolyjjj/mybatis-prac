Mybatis Practice
------------

use mybatis only

### tech stack
* Java8
* Mybatis
* Mysql

### mybatis source follow
Configuration 持有了environment，并通过addMapper的方式添加了对应的Mapper
environment持有了transactionFactory和dataSource

通过Configuration来创建了SqlSessionFactory
通过SqlSessionFactory来打开一个session，也就是调用sqlSessionFactory.openSession()
通过session来执行对应的statement

Configuration持有ExecutorType，default是Executor.SIMPLE

openSession的流程：
* 从configuration中获取Environment来创建TransactionFactory。这里实际上是直接从Environment中get之前set的TransactionFactory。
* 通过这个TransactionFactory来获取一个Transaction对象，其实就是新建了一个JdbcTransaction的实例，传入DataSource ds, TransactionIsolationLevel level, boolean autoCommit
* 通过transaction和executorType来创建一个SimpleExecutor。同时如果cacheEnabled的花，就创建一个CachingExecutor，这个类会代理之前创建的SimpleExecutor。
* 如果有interceptor的话，调用interceptor，传入executor，返回一个新的executor
* 最后创建一个DefaultSqlSession，传入Configuration configuration, Executor executor, boolean autoCommit

然后Blog blog = session.selectOne("li.koly.BlogMapper.selectBlog", 1L);来执行具体的query语句。
这里的selectOne里面分成两步：
* configuration.getMappedStatement(statement)
* executor.query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER)

从dataSource和transactionFactory到session:
* 源头是dataSource和transactionFactory，尽头是一个session
* 中间使用了Environment这个抽象来代表dataSource和transactionFactory
* 又使用了Configuration来代表mybatis的具体配置和Environment
* 然后通过Configuration来创建session，中间通过SqlSessionFactory来做
