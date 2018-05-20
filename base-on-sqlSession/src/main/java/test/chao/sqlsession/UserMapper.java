package test.chao.sqlsession;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import test.chao.base.DataSourceSelector;
import test.chao.base.User;

/**
 * @author xiezhengchao
 * @since 2018/5/20 17:19
 */
@Component
public class UserMapper {

    @Resource
    private SqlSession sqlSession;

    public User selectByPrimaryKeyTest1(Long id) {
        DataSourceSelector.set("test1");
        return sqlSession.selectOne("selectByPrimaryKey", id);
    }

    public User selectByPrimaryKeyTest2(Long id) {
        DataSourceSelector.set("test2");
        return sqlSession.selectOne("selectByPrimaryKey", id);
    }

}
