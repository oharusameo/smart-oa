package com.quxue.template.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum SignStatusEnum implements IEnum<Integer> {
    /**
     * NORMAL:正常
     * LATE:迟到
     * EARLY:早退
     */
    NORMAL("NORMAL", 1),
    LATE("LATE", 2),
    EARLY("EARLY", 3);

    private String desc;
    private Integer value;

    SignStatusEnum(String desc, Integer value) {
        this.desc = desc;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return desc;
    }
}
