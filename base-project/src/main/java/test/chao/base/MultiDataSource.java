package test.chao.base;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 就像AbstractRoutingDataSource一样在获取数据库连接时进行路由
 * 
 * @author xiezhengchao
 * @since 2018/5/20 14:46
 */
public class MultiDataSource implements javax.sql.DataSource {

    private javax.sql.DataSource dataSourceProxy;

    public MultiDataSource(DataSource defaultDataSource, Map<String, DataSource> dataSourceMap) {
        this.dataSourceProxy = (javax.sql.DataSource) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class<?>[] { javax.sql.DataSource.class }, new DataSourceHandle(defaultDataSource, dataSourceMap));
    }


    private class DataSourceHandle implements InvocationHandler {

        private DataSource defaultDataSource;
        private Map<String, DataSource> dataSourceMap;

        DataSourceHandle(DataSource defaultDataSource, Map<String, DataSource> dataSourceMap) {
            this.defaultDataSource = defaultDataSource;
            this.dataSourceMap = dataSourceMap;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.invoke(selectedDataSource(), args);
        }

        private DataSource selectedDataSource() {
            if (DataSourceSelector.get() == null) {
                return defaultDataSource;
            }

            return dataSourceMap.getOrDefault(DataSourceSelector.get(), defaultDataSource);
        }
    }


    @Override
    public Connection getConnection() throws SQLException {
        return dataSourceProxy.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return dataSourceProxy.getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return dataSourceProxy.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        dataSourceProxy.setLogWriter(out);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return dataSourceProxy.getLoginTimeout();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        dataSourceProxy.setLoginTimeout(seconds);
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSourceProxy.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return dataSourceProxy.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return dataSourceProxy.isWrapperFor(iface);
    }

}
