package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.Dept;
import com.quxue.template.service.DeptService;
import com.quxue.template.mapper.DeptMapper;
import org.springframework.stereotype.Service;

/**
* @author harusame
* @description 针对表【t_dept】的数据库操作Service实现
* @createDate 2023-10-17 20:12:16
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService {

}




