package com.quxue.template.common.enums;


import com.baomidou.mybatisplus.annotation.IEnum;

public enum SignTypeEnum implements IEnum<Integer> {
    SIGN_IN("SIGN_IN", 1),
    SIGN_OUT("SIGN_OUT", 0),
    ;

    private String desc;

    private Integer value;


    SignTypeEnum(String desc, Integer value) {
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
