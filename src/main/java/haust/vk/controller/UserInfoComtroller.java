package haust.vk.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import haust.vk.entity.Userinfo;
import haust.vk.service.UserinfoService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("user")
public class UserInfoComtroller {
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody Map registerUserinfo(@RequestBody String userinfo){
		System.out.println(userinfo);
		try {
			userinfo = new String(userinfo.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(userinfo);
		Map userinfoMap = JSON.parseObject(userinfo, Map.class);
		System.out.println(userinfoMap.get("user_email"));
		System.out.println(userinfoMap.get("user_password"));
		
		userinfoMap = userinfoServiceImpl.registerUser(userinfoMap);
		
		return userinfoMap;
	}
	
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map loginUserinfo(@RequestBody String userinfo){
		System.out.println(userinfo);
		try {
			userinfo = new String(userinfo.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(userinfo);
		Map userinfoMap = JSON.parseObject(userinfo, Map.class);
		userinfoMap = userinfoServiceImpl.loginUser(userinfoMap);
		if(userinfoMap == null){
			userinfoMap = new HashMap();
			userinfoMap.put("loginerror", "error");
			return userinfoMap;
		}
		return userinfoMap;
	}
}
