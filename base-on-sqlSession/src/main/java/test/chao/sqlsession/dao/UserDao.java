package test.chao.sqlsession.dao;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import test.chao.base.SwitchDataSource;
import test.chao.base.User;

/**
 * @author xiezhengchao
 * @since 2018/5/20 17:19
 */
@Component
public class UserDao extends BaseDaoImpl {

    @Resource
    private SqlSession sqlSession;

    @SwitchDataSource("test1")
    public User selectByPrimaryKeyTest1(Long id) {
        return sqlSession.selectOne("selectByPrimaryKey", id);
    }

    @SwitchDataSource("test2")
    public User selectByPrimaryKeyTest2(Long id) {
        return sqlSession.selectOne("selectByPrimaryKey", id);
    }

}
