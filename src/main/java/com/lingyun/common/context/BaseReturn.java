package com.lingyun.common.context;

import com.lingyun.common.code.ErrorMessage;
import com.lingyun.common.code.MessageLevelEnum;

public class BaseReturn {

    private boolean success;
    private String message;
    /**
     * level可返回警告，消息，错误几种级别，方便前端根据此字段使用样式，默认值是info
     */
    private String level = MessageLevelEnum.INFO.getCode();

    private Object data;

    public BaseReturn() {

    }

    public BaseReturn(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public BaseReturn(boolean success, String message, String level, Object data) {
        this.success = success;
        this.message = message;
        this.level = level;
        this.data = data;
    }

    public BaseReturn(ErrorMessage errorMessage, Object data) {
        this.success = errorMessage.isSuccess();
        this.message = errorMessage.getMessage();
        this.data = data;
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
