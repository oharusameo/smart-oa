package com.quxue.template.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum SignEnum implements IEnum<String> {
    WORKDAY("workday", "工作日"),
    HOLIDAY("holiday", "节假日"),
    ABSENCE("absence", "absence"),
    NORMAL("normal", "normal"),
    ABNORMAL("abnormal", "abnormal"),
    ;
    private String desc;
    private String value;

    SignEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return desc;
    }
}
