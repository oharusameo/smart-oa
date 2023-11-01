package com.quxue.template.mapper;

import com.quxue.template.domain.pojo.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;
import java.util.Map;

/**
 * @author ggzst
 * @description 针对表【t_tenant】的数据库操作Mapper
 * @createDate 2023-10-31 11:27:04
 * @Entity com.quxue.template.domain.pojo.Tenant
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    @Insert("insert into t_tenant (name, apply_date, t_status, address, applicant, telephone, maturity) values (#{name},current_date,#{tStatus},#{address},#{applicant},#{telephone},ADDDATE(current_date,INTERVAL 7 DAY))")
    @SelectKey(statement = "select LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Integer.class)
    Integer insertTrialTenant(Tenant tenant);

    @Select("select name from t_tenant where id=#{tenantId} and t_status != 0")
    String selectName(String tenantId);

    @Select("select id from t_tenant where maturity < now() and t_status=2")
    List<Integer> selectExpireTenant();


    @Select("select status us ,t_status ts from t_user ut  left join t_tenant tt on tenant_id = tt.id where tt.id=#{tenantId}")
    Map<String, String> selectExpire(String tenantId);

    @Select("select t_status from t_user left join t_tenant tt on tenant_id = tt.id where t_user.id =#{userId}")
    String selectExpireByUserId(Integer userId);
}




