package test.chao.sqlsession.dao;

import test.chao.base.User;

/**
 * @author xiezhengchao
 * @since 2018/5/20 15:24
 */
public interface UserDao extends BaseDao {

    User selectByPrimaryKeyTest1(Long id);

    User selectByPrimaryKeyTest2(Long id);

}
