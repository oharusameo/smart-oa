package com.quxue.template.mapper;

import cn.hutool.core.date.DateTime;
import com.quxue.template.domain.pojo.Workday;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author harusame
 * @description 针对表【t_workday】的数据库操作Mapper
 * @createDate 2023-10-17 20:12:16
 * @Entity com.quxue.template.domain.pojo.Workday
 */
public interface WorkdayMapper extends BaseMapper<Workday> {

    @Select("select * from t_workday where year(date)=year(curdate())")
    List<Workday> selectWorkDay4CurrentYear();

    @Select("select  id from t_workday where date=current_date limit 1")
    Integer isTodayWorkDay();

    @Select("select date from t_workday where date between #{startDate} and #{endDate}")
    List<String> getWorkdaysInRange(DateTime startDate, DateTime endDate);
}




