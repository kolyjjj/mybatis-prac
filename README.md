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


xml或者interface中的sql语句会被加载到内存中来，也就意味着内存中会有这些sql statement的一个表示。运行时就是对象，
有对象就是有模板，也就是class，这个class是MappedStatement;

* 这个MappedStatement是通过MapperBuilderAssistant.parseStatement方法来解析并填充的。
* 填充好一个MappedStatement对象之后，将其放入configuration的Map<String, MappedStatement> mappedStatements中
* 一个MappedStatement包含了具体的SQL语句(可能是带参数的)，这些用一个SqlSource来表示。以及返回的类型.
* 同时一个MappedStatement也包含了一个configuration。所以MappedStatement和configuration是一个多对一的关系，
然后彼此持有对方的引用

整个mybatis的过程可以分成几个部分：
* 解析xml或者Mapper Interface中的Annotation。这个过程的输出是一系列的MappedStatement。
* 执行具体的sql语句。这里要考虑Cache的事情，interceptor的事情，以及返回的resultSet到具体的对象的问题。
