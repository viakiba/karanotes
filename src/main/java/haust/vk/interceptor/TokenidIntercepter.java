package haust.vk.interceptor;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonFormatErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.UserinfoService;
import haust.vk.utils.JsonRequestWrapper;

/**
 * tokenid 拦截器
 * @author viakiba
 *
 */
public class TokenidIntercepter extends HandlerInterceptorAdapter{
	
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Map info = (Map) request.getAttribute("info");
		Map selectUserloginByTokenid = userinfoServiceImpl.selectUserloginByTokenid((String) info.get("token_id"));
		if(selectUserloginByTokenid == null){
			System.out.println("");
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		request.setAttribute("user", "");
		return true;
	}
}
