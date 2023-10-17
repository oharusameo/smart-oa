package com.quxue.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quxue.template.domain.pojo.Dept;
import com.quxue.template.service.DeptService;
import com.quxue.template.mapper.DeptMapper;
import org.springframework.stereotype.Service;

/**
* @author ggzst
* @description 针对表【t_dept】的数据库操作Service实现
* @createDate 2023-10-17 19:02:12
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService{

}




