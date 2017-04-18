package haust.vk.result;

import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.exception.interfaces.MesscodeInterface;

public class ResultBody {
	/**
     * 响应代码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应结果
     */
    private Object result;

    public ResultBody(MesscodeInterface errorInfo) {
        this.code = errorInfo.getCode();
        this.message = errorInfo.getMessage();
    }

    public ResultBody(Object result) {
        this.code = SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE.getCode();
        this.message = SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE.getMessage();
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
