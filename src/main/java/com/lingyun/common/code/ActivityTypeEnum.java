package com.lingyun.common.code;

public enum  ActivityTypeEnum {
    COMMON("common"),CUSTOM("custom");
    private String code;
    private ActivityTypeEnum(String code) {
        this.code = code;
    }
    public String getCode() {
        return code;
    }
}
