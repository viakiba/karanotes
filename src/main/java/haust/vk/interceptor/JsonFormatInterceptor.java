package haust.vk.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonFormatErrorInfoEnum;
import haust.vk.utils.JsonRequestWrapper;

/**
 * 
 * @author viakiba
 *
 */
public class JsonFormatInterceptor extends HandlerInterceptorAdapter{
	private static Logger logger = Logger.getLogger(JsonFormatInterceptor.class);
	
	/**
	 * json处理成map  从请求体中拿到json
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		JsonRequestWrapper myJsonRequestWrapper = new JsonRequestWrapper(request);
		logger.info(myJsonRequestWrapper.getBody());
		Map jsoninfo = null;
		try{
			jsoninfo = JSON.parseObject(myJsonRequestWrapper.getBody(), Map.class);
		}catch(Exception e){
			throw new GlobalErrorInfoException(JsonFormatErrorInfoEnum.BODY_IS_NOT_JSON);
		}
		myJsonRequestWrapper.setAttribute("jsoninfo", jsoninfo);
		logger.info("进入jsonformat"+jsoninfo);
		return true;
	}
}
