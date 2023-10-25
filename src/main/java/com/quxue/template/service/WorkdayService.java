package com.quxue.template.service;

import com.quxue.template.domain.dto.SpecialWorkdayDTO;
import com.quxue.template.domain.pojo.Workday;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author harusame
 * @description 针对表【t_workday】的数据库操作Service
 * @createDate 2023-10-17 20:12:16
 */
public interface WorkdayService extends IService<Workday> {

    void addSpecialWorkDay(SpecialWorkdayDTO specialWorkdayDTO);

    List<Workday> listWorkDay4CurrentYear();
}
