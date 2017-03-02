package haust.vk.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import haust.vk.entity.Userinfo;
import haust.vk.service.UserinfoService;
import haust.vk.utils.JsonToMap;

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
	
	@Resource
	private JsonToMap jsonToMap;
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public @ResponseBody Map registerUserinfo(@RequestBody String userinfo){
		Map userinfoMap = new HashMap();
		try {
			userinfoMap = jsonToMap.jsonToMapUtil(userinfo);
		} catch (Exception e) {
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", 3);
			return userinfoMap;
		}
		
		try {
			userinfoMap = userinfoServiceImpl.registerUser(userinfoMap);
		} catch (Exception e) {
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", "5 不可预知错误");
			return userinfoMap;
		}
		
		if(userinfoMap == null){
			userinfoMap = new HashMap();
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", 1);
			return userinfoMap;
		}
		userinfoMap.put("success", 1);
		userinfoMap.put("messcode", 2);
		return userinfoMap;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Map loginUserinfo(@RequestBody String userinfo){
		Map userinfoMap = new HashMap();
		try {
			userinfoMap = jsonToMap.jsonToMapUtil(userinfo);
		} catch (Exception e) {
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", 3);
			return userinfoMap;
		}
		
		try {
			userinfoMap = userinfoServiceImpl.loginUser(userinfoMap);
		} catch (Exception e) {
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", 2);
			return userinfoMap;
		}
		
		if(userinfoMap == null ){
			userinfoMap = new HashMap();
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", 1);
			return userinfoMap;
		}
		return userinfoMap;
	}
	
	@RequestMapping(value="/checkemail")
	public @ResponseBody Map checkEmail(String email) throws UnsupportedEncodingException{
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
	
	@RequestMapping(value="/checktoken")
	public @ResponseBody Map checkToken(String token_id) throws UnsupportedEncodingException{
		Map infoMap = new HashMap();
		token_id = new String(token_id.getBytes("ISO-8859-1"),"UTF-8");
		try{
			infoMap = userinfoServiceImpl.selectUserloginByTokenid(token_id);
		}catch(Exception e){
			infoMap.put("success", -1);
			infoMap.put("messcode", "5 不可预见的 异常");
			e.printStackTrace();
			return infoMap;
		}
		return infoMap;
	}
	
	@RequestMapping(value="/checkpath")
	public @ResponseBody Map checkPath(String user_path) throws UnsupportedEncodingException{
		Map infoMap = null;
		user_path = new String(user_path.getBytes("ISO-8859-1"),"UTF-8");
		try{
			infoMap = userinfoServiceImpl.selectByUserpath(user_path);
		}catch(Exception e){
			infoMap = new HashMap();
			infoMap.put("success", -1);
			infoMap.put("messcode", "5 不可预见的 异常");
			e.printStackTrace();
			return infoMap;
		}
		return infoMap;
	}
	
	@RequestMapping(value="/updateuserinfo",method=RequestMethod.POST)
	public @ResponseBody Map updateUserinfo(@RequestBody String user_path) throws UnsupportedEncodingException{
		Map infoMap = null;
		try {
			infoMap = jsonToMap.jsonToMapUtil(user_path);
		} catch (Exception e1) {
			infoMap.put("success", -1);
			infoMap.put("messcode", 3);
			e1.printStackTrace();
			return infoMap;
		}

		try{
			infoMap = userinfoServiceImpl.updateUserinfo(infoMap);
		}catch(Exception e){
			infoMap = new HashMap();
			infoMap.put("success", -1);
			infoMap.put("messcode", "5 不可预见的 异常");
			e.printStackTrace();
			return infoMap;
		}
		return infoMap;
	}
	
	@RequestMapping(value="/updateuseremail",method=RequestMethod.POST)
	public @ResponseBody Map updateUserEmail(@RequestBody String useremail) throws UnsupportedEncodingException{
		Map infoMap = null;
		try {
			infoMap = jsonToMap.jsonToMapUtil(useremail);
		} catch (Exception e1) {
			infoMap.put("success", -1);
			infoMap.put("messcode", 3);
			e1.printStackTrace();
			return infoMap;
		}
		
		try{
			infoMap = userinfoServiceImpl.updateUseremail(infoMap);
		}catch(Exception e){
			infoMap = new HashMap();
			infoMap.put("success", -1);
			infoMap.put("messcode", "5 不可预见的 异常");
			e.printStackTrace();
			return infoMap;
		}
		return infoMap;
	}
	
	@RequestMapping(value="/updateuserpass",method=RequestMethod.POST)
	public @ResponseBody Map updateUserPass(@RequestBody String userpass) throws UnsupportedEncodingException{
		Map infoMap = null;
		try {
			infoMap = jsonToMap.jsonToMapUtil(userpass);
		} catch (Exception e1) {
			infoMap.put("success", -1);
			infoMap.put("messcode", 3);
			e1.printStackTrace();
			return infoMap;
		}
		
		try{
			infoMap = userinfoServiceImpl.updateUserpass(infoMap);
		}catch(Exception e){
			infoMap = new HashMap();
			infoMap.put("success", -1);
			infoMap.put("messcode", "5 不可预见的 异常");
			e.printStackTrace();
			return infoMap;
		}
		return infoMap;
	}
}
