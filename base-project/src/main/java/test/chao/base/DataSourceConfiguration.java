package test.chao.base;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiezhengchao
 * @since 2018/5/20 15:05
 */
@Configuration
@PropertySource("classpath:datasource.properties")
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "multi.datasource1")
    public DataSource dataSource1() {
        return new DataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "multi.datasource2")
    public DataSource dataSource2() {
        return new DataSource();
    }

    @Bean
    @Primary
    public javax.sql.DataSource dataSource() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(2);
        dataSourceMap.put("test1", dataSource1());
        dataSourceMap.put("test2", dataSource2());
        initH2DB(dataSource1(), classPath2Str("sql/schema.sql"), classPath2Str("sql/data1.sql"));
        initH2DB(dataSource2(), classPath2Str("sql/schema.sql"), classPath2Str("sql/data2.sql"));
        return new MultiDataSource(dataSource1(), dataSourceMap);
    }

    private void initH2DB(DataSource dataSource, String schemaSql, String dataSql) {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection(dataSource.getUrl());
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(schemaSql);
            stmt.executeUpdate(dataSql);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String classPath2Str(String classPath) {
        try {
            InputStream is = new ClassPathResource(classPath).getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = is.read(buf)) != -1) {
                outputStream.write(buf, 0, n);
            }
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
