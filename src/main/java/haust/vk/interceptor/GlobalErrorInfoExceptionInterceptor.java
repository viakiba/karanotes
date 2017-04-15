package haust.vk.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.interfaces.MesscodeInterface;

/**
 * 异常处理响应类
 * @author viakiba
 */
@ControllerAdvice
public class GlobalErrorInfoExceptionInterceptor extends HandlerInterceptorAdapter{
	
	/**
	 * 异常拦截方法
	 * @Author : viakiba
	 * @param req
	 * @param exceptioninfo
	 * @return
	 * 2017-04-15
	 */
	@ExceptionHandler(value=GlobalErrorInfoException.class)
	public @ResponseBody Map errorInfoHandlerOverJson(HttpServletRequest req,GlobalErrorInfoException exceptioninfo){
		Map<Object, Object> map = new HashMap<>();
        MesscodeInterface info = exceptioninfo.getMesscodeInterface();
        System.out.println(info.getCode()+"======"+info.getMessage());
        map.put("code", info.getCode());
        map.put("message", info.getMessage());
        return map;
	}
}
