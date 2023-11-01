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

    @Select("select * from t_workday where year(date)=year(curdate()) and  tenant_id=#{tenantId}")
    List<Workday> selectWorkDay4CurrentYear(String tenantId);

    @Select("select  id from t_workday where date=current_date and tenant_id=#{tenantId} limit 1")
    Integer isTodayWorkDay(String tenantId);

    @Select("select date from t_workday where date between #{startDate} and #{endDate} and tenant_id=#{tenantId}")
    List<String> getWorkdaysInRange(DateTime startDate, DateTime endDate,String tenantId);
}




