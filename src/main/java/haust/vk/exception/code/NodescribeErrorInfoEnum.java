package haust.vk.exception.code;

import haust.vk.exception.interfaces.MesscodeInterface;

public enum NodescribeErrorInfoEnum implements MesscodeInterface{
	NO_DESCRIBE_ERROR("5","服务器异常不可描述");
	
	private String code;
	private String message;
	
	private NodescribeErrorInfoEnum(String code,String message) {
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
