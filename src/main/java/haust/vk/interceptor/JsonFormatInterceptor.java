package haust.vk.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonFormatErrorInfoEnum;
import haust.vk.utils.JsonRequestWrapper;

public class JsonFormatInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		JsonRequestWrapper myJsonRequestWrapper = new JsonRequestWrapper(request);
		Map info = null;
		try{
			info = JSON.parseObject(myJsonRequestWrapper.getBody(), Map.class);
		}catch(Exception e){
			throw new GlobalErrorInfoException(JsonFormatErrorInfoEnum.BODY_IS_NOT_JSON);
		}
		myJsonRequestWrapper.setAttribute("map", info);
		return true;
	}
}
