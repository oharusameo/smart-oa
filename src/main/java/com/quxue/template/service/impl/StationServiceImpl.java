package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.Station;
import com.quxue.template.service.StationService;
import com.quxue.template.mapper.StationMapper;
import org.springframework.stereotype.Service;

/**
* @author harusame
* @description 针对表【t_station】的数据库操作Service实现
* @createDate 2023-10-17 20:12:16
*/
@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station>
    implements StationService{

}




