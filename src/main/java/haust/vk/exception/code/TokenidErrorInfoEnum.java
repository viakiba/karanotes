package haust.vk.exception.code;

import haust.vk.exception.interfaces.MesscodeInterface;

public enum TokenidErrorInfoEnum implements MesscodeInterface {
	USER_CONNOT_BE_FOUND("2","用户不存在");
	
	private String code;
	private String message;
	
	private TokenidErrorInfoEnum(String code,String message) {
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
