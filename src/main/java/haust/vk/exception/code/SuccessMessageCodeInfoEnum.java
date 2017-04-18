package haust.vk.exception.code;

import haust.vk.exception.interfaces.MesscodeInterface;

public enum SuccessMessageCodeInfoEnum implements MesscodeInterface{
	SUCCESS_CODE_MESSAGE("1","邮箱可以使用"),
	FAIL_CODE_MESSAGE("0","邮箱不可以使用");
	
	private String code;
	private String message;
	
	private SuccessMessageCodeInfoEnum(String code,String message) {
		this.code = code;
		this.message = message;
	}
	
	@Override
	public String getCode() {
		return this.code;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

}
