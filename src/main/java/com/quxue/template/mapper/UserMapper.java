package com.quxue.template.mapper;

import com.quxue.template.domain.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author harusame
 * @description 针对表【t_user】的数据库操作Mapper
 * @createDate 2023-10-17 20:12:16
 * @Entity com.quxue.template.domain.pojo.User
 */
public interface UserMapper extends BaseMapper<User> {

    @Update("update t_user set open_id=#{openId},status=1 where id=#{id} and status=0")
    Integer activateUser(User user);
}




