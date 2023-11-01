package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.common.utils.TokenUtils;
import com.quxue.template.domain.dto.SpecialWorkdayDTO;
import com.quxue.template.domain.pojo.Holidays;
import com.quxue.template.domain.pojo.Workday;
import com.quxue.template.exception.BusinessException;
import com.quxue.template.exception.SystemException;
import com.quxue.template.service.WorkdayService;
import com.quxue.template.mapper.WorkdayMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author harusame
 * @description 针对表【t_workday】的数据库操作Service实现
 * @createDate 2023-10-17 20:12:16
 */
@Service
public class WorkdayServiceImpl extends ServiceImpl<WorkdayMapper, Workday>
        implements WorkdayService {
    @Resource
    private WorkdayMapper workdayMapper;
    @Resource
    private TokenUtils tokenUtils;

    @Override
    public void addSpecialWorkDay(SpecialWorkdayDTO specialWorkdayDTO) {
        Workday workday = new Workday();
        String tenantId = tokenUtils.getTenantIdFromHeader();
        workday.setTenantId(Integer.valueOf(tenantId));
        workday.setDate(specialWorkdayDTO.getSpecialWorkday());
        if (workdayMapper.insert(workday) != 1) {
            throw new SystemException("系统异常，添加特殊工作日失败");
        }
    }

    @Override
    public List<Workday> listWorkDay4CurrentYear() {
        String tenantId = tokenUtils.getTenantIdFromHeader();
        return workdayMapper.selectWorkDay4CurrentYear(tenantId);
    }

    @Override
    public boolean removeByIdAndTid(Integer workdayId) {
        String tenantId = tokenUtils.getTenantIdFromHeader();
        int r = workdayMapper.delete(new QueryWrapper<Workday>().eq("id", workdayId).eq("tenant_id", tenantId));
        return r == 1;
    }
}




