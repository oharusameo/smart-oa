package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.Holidays;
import com.quxue.template.service.HolidaysService;
import com.quxue.template.mapper.HolidaysMapper;
import org.springframework.stereotype.Service;

/**
* @author harusame
* @description 针对表【t_holidays】的数据库操作Service实现
* @createDate 2023-10-17 20:12:16
*/
@Service
public class HolidaysServiceImpl extends ServiceImpl<HolidaysMapper, Holidays>
    implements HolidaysService {

}




