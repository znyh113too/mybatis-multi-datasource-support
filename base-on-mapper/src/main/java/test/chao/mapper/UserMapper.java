package test.chao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import test.chao.base.User;

/**
 * @author xiezhengchao
 * @since 2018/5/20 15:24
 */
@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{0}")
    @SwitchDataSource("test1")
    User selectByPrimaryKeyTest1(Long id);

    @Select("select * from user where id=#{0}")
    @SwitchDataSource("test2")
    User selectByPrimaryKeyTest2(Long id);

}
