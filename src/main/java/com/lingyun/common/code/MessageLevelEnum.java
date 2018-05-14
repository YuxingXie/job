package com.lingyun.common.code;

public enum MessageLevelEnum {
    INFO("info"),WARNING("warning"),WRONG("wrong");
    private String code;
    MessageLevelEnum(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }
}
