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
		Map userinfoMap = new HashMap();
		try {
			userinfo = new String(userinfo.getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(userinfo);
			userinfoMap = JSON.parseObject(userinfo, Map.class);
		} catch (Exception e) {
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("error", 1);
			return userinfoMap;
		}
		
		try {
			userinfoMap = userinfoServiceImpl.registerUser(userinfoMap);
		} catch (Exception e) {
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("error", 2);
			return userinfoMap;
		}
		
		if(userinfoMap == null){
			userinfoMap = new HashMap();
			userinfoMap.put("success", -1);
			userinfoMap.put("error", 3);
			return userinfoMap;
		}
		userinfoMap.put("success", 1);
		return userinfoMap;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map loginUserinfo(@RequestBody String userinfo){
		Map userinfoMap = new HashMap();
		try {
			userinfo = new String(userinfo.getBytes("ISO-8859-1"),"UTF-8");
			userinfoMap = JSON.parseObject(userinfo, Map.class);
		} catch (Exception e) {
			userinfoMap.put("success", -1);
			userinfoMap.put("error", 1);
			return userinfoMap;
		}
		
		try {
			userinfoMap = userinfoServiceImpl.loginUser(userinfoMap);
		} catch (Exception e) {
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("error", 2);
			return userinfoMap;
		}
		
		if(userinfoMap == null ){
			userinfoMap = new HashMap();
			userinfoMap.put("success", -1);
			userinfoMap.put("error", 3);
			return userinfoMap;
		}
		userinfoMap.put("success", 1);
		return userinfoMap;
	}
	
	@RequestMapping(value="/checkemail")
	public @ResponseBody Map checkemail(String email) throws UnsupportedEncodingException{
		Map infoMap = new HashMap();
		email = new String(email.getBytes("ISO-8859-1"),"UTF-8");
		
		try{
			infoMap = userinfoServiceImpl.selectByEmail(email);
		}catch(Exception e){
			e.printStackTrace();
			infoMap.put("success", -1);
			infoMap.put("messcode", 3);
		}
		return infoMap;
	}
}
