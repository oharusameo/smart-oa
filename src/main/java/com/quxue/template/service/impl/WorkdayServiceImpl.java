package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.Workday;
import com.quxue.template.service.WorkdayService;
import com.quxue.template.mapper.WorkdayMapper;
import org.springframework.stereotype.Service;

/**
* @author harusame
* @description 针对表【t_workday】的数据库操作Service实现
* @createDate 2023-10-17 20:12:16
*/
@Service
public class WorkdayServiceImpl extends ServiceImpl<WorkdayMapper, Workday>
    implements WorkdayService{

}




