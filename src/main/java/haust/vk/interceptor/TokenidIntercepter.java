package haust.vk.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonFormatErrorInfoEnum;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.UserinfoService;
import haust.vk.utils.JsonRequestWrapper;

/**
 * tokenid 拦截器
 * @author viakiba
 *
 */
public class TokenidIntercepter extends HandlerInterceptorAdapter{
	private static Logger logger = Logger.getLogger(TokenidIntercepter.class);
	
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map jsoninfo = (Map) request.getAttribute("jsoninfo");
		logger.info(jsoninfo+"TokenidIntercepter");
		String token_id = (String) jsoninfo.get("token_id");
		
		if(token_id == null || "null".equals(token_id) || "".equals(token_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		Userinfo userinfo = userinfoServiceImpl.selectUserinfoByTokenid(token_id);
		if(userinfo == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		request.setAttribute("userinfo", userinfo);
		return true;
	}
}
