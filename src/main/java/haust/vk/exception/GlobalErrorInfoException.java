package haust.vk.exception;

import haust.vk.exception.interfaces.MesscodeInterface;

public class GlobalErrorInfoException extends Exception{
	private MesscodeInterface messcodeInterface;
	
	public GlobalErrorInfoException(MesscodeInterface messcodeInterface) {
		this.messcodeInterface = messcodeInterface;
	}
	
	public void setMesscodeInterface(MesscodeInterface messcodeInterface) {
		this.messcodeInterface = messcodeInterface;
	}
	
	public MesscodeInterface getMesscodeInterface() {
		return messcodeInterface;
	}
}
