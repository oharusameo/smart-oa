package com.quxue.template.mapper;

import cn.hutool.core.date.DateTime;
import com.quxue.template.domain.pojo.SignDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quxue.template.domain.vo.DateVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author harusame
 * @description 针对表【t_sign_detail】的数据库操作Mapper
 * @createDate 2023-10-17 20:12:16
 * @Entity com.quxue.template.domain.pojo.SignDetail
 */
public interface SignDetailMapper extends BaseMapper<SignDetail> {

    List<DateVo> getSignStatistics(String id, DateTime startDate, DateTime endDate);

    @Select("select  id from t_sign_detail where user_id=#{userId} && sign_type=1 && sign_date=current_date limit 1 ")
    SignDetail selectSignInRecord(String userId);

    @Select("select  id from t_sign_detail where user_id=#{userId} && sign_type=0 && sign_date=current_date limit 1 ")
    SignDetail selectSignOutRecord(String userId);
}




