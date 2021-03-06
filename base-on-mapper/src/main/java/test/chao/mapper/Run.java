package test.chao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import test.chao.base.DataSourceConfiguration;
import test.chao.base.User;

import javax.annotation.Resource;

/**
 * 基于Mybatis的Mapper代理拓展进行切换数据库
 * 
 * @author xiezhengchao
 * @since 2018/5/20 14:43
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan(basePackages = "test.chao.mapper", annotationClass = Mapper.class, factoryBean = MapperFactoryBeanExt.class)
@Import(DataSourceConfiguration.class)
public class Run implements CommandLineRunner {

    @Resource
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }

    @Override
    public void run(String...args) throws Exception {
        User user1 = userMapper.selectByPrimaryKeyTest1(1L);
        System.out.println("db1 user:" + user1);

        User user2 = userMapper.selectByPrimaryKeyTest2(1L);
        System.out.println("db2 user:" + user2);
    }
}
