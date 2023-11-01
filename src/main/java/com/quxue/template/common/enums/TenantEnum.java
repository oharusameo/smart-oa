package com.quxue.template.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum TenantEnum implements IEnum<Integer> {
    FORMAL("formal", 1),
    STOP("stop", 0),
    TRIAL("trial", 2);
    private String desc;
    private Integer value;

    TenantEnum(String desc, Integer value) {
        this.desc = desc;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
