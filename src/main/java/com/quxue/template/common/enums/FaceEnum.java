package com.quxue.template.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum FaceEnum implements IEnum<Integer> {

    NO_FACE_MODEL("no face model", 100404),
    FACE_MODEL_MISMATCH("face model mismatch", 100405),
    NOT_LIVE_FACE("not live face", 100406);
    private Integer value;
    private String desc;

    FaceEnum(String desc, Integer value) {
        this.value = value;
        this.desc = desc;
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
