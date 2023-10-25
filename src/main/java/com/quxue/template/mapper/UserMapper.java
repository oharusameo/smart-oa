package com.quxue.template.mapper;

import com.quxue.template.domain.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * @author harusame
 * @description 针对表【t_user】的数据库操作Mapper
 * @createDate 2023-10-17 20:12:16
 * @Entity com.quxue.template.domain.pojo.User
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update t_user set open_id=#{openId},status=1 where id=#{id} and status=0")
    Integer activateUser(User user);

    @Select("select id from t_user where open_id=#{openId}")
    String selectUserId(String openId);

    @Select("select root from t_user where id=#{id}")
    Integer selectJobById(String id);

    @Select("select username from t_user where id=#{id}")
    String selectUserNameById(String id);

    @Select("select update_user from t_user where id=#{id}")
    String selectUpdateUserById(String id);
}




