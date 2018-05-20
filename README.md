# mybatis-multi-datasource-support
基于Mybatis的DataSource拓展的多数据封装(更灵活简单,不支持JTA)


# 前言
这个包只是一个演示,需要修改之后再放到项目中使用.

# 包功能简介
**base-project**:提供了多数据源的配置.
**base-on-mapper**:提供了基于Mybatis的Mapper接口返回代理的多数据源切换实现
**base-on-sqlSession**提供了基于Mybatis的SqlSession执行时多数据源切换实现

# 简要说明
数据库使用的是H2内存数据库,可以把项目拉下来直接跑看效果.
这个是基于DataSource的拓展所以不支持JTA这一个是[支持JTA的多数据源配置](https://github.com/znyh113too/bubi-mybatis-spring-boot "")


![苍老师](https://raw.githubusercontent.com/znyh113too/mybatis-multi-datasource-support/master/picture/4da3-fysnevm4755206.jpg)
