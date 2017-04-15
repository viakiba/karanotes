package haust.vk.exception.code;

import haust.vk.exception.interfaces.MesscodeInterface;

public enum JsonFormatErrorInfoEnum implements MesscodeInterface{
	BODY_IS_NOT_JSON("3","JSON格式错误");
	
	private String code;
	private String message;
	
	private JsonFormatErrorInfoEnum(String code,String message) {
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
