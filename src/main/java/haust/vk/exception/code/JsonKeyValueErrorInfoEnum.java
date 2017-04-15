package haust.vk.exception.code;

import haust.vk.exception.interfaces.MesscodeInterface;

public enum JsonKeyValueErrorInfoEnum implements MesscodeInterface{
	JSON_KEYVALUE_ERROR("4","json键值对不符合规范");
	
	private String code;
	private String message;
	
	private JsonKeyValueErrorInfoEnum(String code,String message) {
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
