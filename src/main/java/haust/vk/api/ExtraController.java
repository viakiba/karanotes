package haust.vk.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.service.UserinfoService;
import haust.vk.utils.JsonRequestWrapper;

@Controller
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class ExtraController {
	
	private static Logger logger = Logger.getLogger(ExtraController.class);
	
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	@RequestMapping(value="/")
	public @ResponseBody Map CrosHelpResp(HttpServletRequest req, HttpServletResponse resp){
		Map map = new HashMap<>();
		map.put("1", "响应跨域头信息");
		return map;
	}
	
	
	/**
	 * get跨域
	 */
	//@CrossOrigin(methods={RequestMethod.GET, RequestMethod.POST})
	@RequestMapping(value="/extra/checkemails")
	public @ResponseBody Map checkEmails(HttpServletRequest req,HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		String email = req.getParameter("email");
		logger.info(email);
		Map map = new HashMap<>();
		map.put("code", "1");
		resp.addHeader(" Access-Control-Allow-Origin", "*");
		return map;
	}
	
	/**
	 * post  json 跨域测试
	 */
	//@CrossOrigin(methods={RequestMethod.GET, RequestMethod.POST})
	@RequestMapping(value="/extra/checkemailss")
	public @ResponseBody Map checkEmailss(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		JsonRequestWrapper json = new JsonRequestWrapper(req);
		String body = json.getBody();
		logger.info(body);
		Map map = new HashMap<>();
		map.put("code", "1");
		return map;
	}
}
