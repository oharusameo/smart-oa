package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.dto.SpecialDayDTO;
import com.quxue.template.domain.pojo.Holidays;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.exception.SystemException;
import com.quxue.template.service.HolidaysService;
import com.quxue.template.mapper.HolidaysMapper;
import org.simpleframework.xml.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author harusame
 * @description 针对表【t_holidays】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
public class HolidaysServiceImpl extends ServiceImpl<HolidaysMapper, Holidays>
        implements HolidaysService {

    @Resource
    private HolidaysMapper holidaysMapper;

    @Override
    public void addSpecialHoliday(SpecialDayDTO specialDayDTO) {
        Holidays holidays = new Holidays();
        holidays.setDate(specialDayDTO.getSpecialHoliday());
        if (holidaysMapper.insert(holidays) != 1) {
            throw new SystemException("系统异常，添加特殊节假日失败");
        }
    }
}




