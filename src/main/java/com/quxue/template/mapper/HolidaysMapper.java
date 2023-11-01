package com.quxue.template.mapper;

import cn.hutool.core.date.DateTime;
import com.quxue.template.domain.pojo.Holidays;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author harusame
 * @description 针对表【t_holidays】的数据库操作Mapper
 * @createDate 2023-10-17 20:12:16
 * @Entity com.quxue.template.domain.pojo.Holidays
 */
public interface HolidaysMapper extends BaseMapper<Holidays> {

    @Select("select * from t_holidays where year(date)=year(curdate()) and tenant_id =#{tenantId}")
    List<Holidays> selectSpecialHolidays4CurrentYear(String tenantId);

    @Select("select  id from t_holidays where date=current_date and tenant_id=#{tenantId}")
    Integer isTodayHoliday(String tenantId);

    @Select("select date from t_holidays where date between #{startDate} and #{endDate} and tenant_id=#{tenantId}")
    List<String> getHolidaysInRange(DateTime startDate, DateTime endDate, String tenantId);
}




