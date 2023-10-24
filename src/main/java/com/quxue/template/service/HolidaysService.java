package com.quxue.template.service;

import com.quxue.template.domain.dto.SpecialDayDTO;
import com.quxue.template.domain.pojo.Holidays;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author harusame
 * @description 针对表【t_holidays】的数据库操作Service
 * @createDate 2023-10-17 20:12:16
 */
public interface HolidaysService extends IService<Holidays> {

    void addSpecialHoliday(SpecialDayDTO specialDayDTO);
}
