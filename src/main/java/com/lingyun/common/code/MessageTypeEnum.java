package com.lingyun.common.code;

public enum MessageTypeEnum {
    ADD("add"),
    DELETE("delete"),
    SELECT("select");

    private String messageType;
    MessageTypeEnum(String messageType) {
        this.messageType=messageType;
    }

    public String getMessageType() {
        return messageType;
    }


}
