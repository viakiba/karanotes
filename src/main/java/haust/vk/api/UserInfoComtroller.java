package haust.vk.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.UserinfoService;
import haust.vk.utils.SendMail;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoComtroller {
	private static Logger logger = Logger.getLogger(UserInfoComtroller.class);
	@Resource
	private UserinfoService userinfoServiceImpl;
	@RequestMapping(value="/checkemail",method=RequestMethod.GET)
	public Map checkEmail(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		String email = req.getParameter("email");
		Userinfo userinfo = null;
		Map map = new HashMap<>();
		try{
			userinfo = userinfoServiceImpl.selectByEmail(email);
		}catch(Exception e){
			logger.error("checkEmail", e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		logger.info(userinfo);
		if(userinfo == null){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
		}else{
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
	}
	
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	public ResultBody registerUserinfo(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Map userinfoMap = (Map) req.getAttribute("jsoninfo");
		String user_email = userinfoMap.get("user_email").toString();
		String user_password = userinfoMap.get("user_password").toString();
		if( user_email == null | user_password == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			userinfoMap = userinfoServiceImpl.registerUser(userinfoMap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(userinfoMap);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResultBody loginUserinfo(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Map userinfoMap = (Map) req.getAttribute("jsoninfo");
		String user_email = userinfoMap.get("user_email").toString();
		String user_password = userinfoMap.get("user_password").toString();
		if(user_email == null | user_password == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			userinfoMap = userinfoServiceImpl.loginUser(userinfoMap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(userinfoMap);
	}
	
	@RequestMapping(value="/checktoken",method=RequestMethod.GET)
	public ResultBody checkToken(String tokenid) throws UnsupportedEncodingException, GlobalErrorInfoException{
		if(tokenid == null | tokenid.length() == 0){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Map infoMap = new HashMap();
		try{
			 Userinfo userInfo = userinfoServiceImpl.selectUserloginByTokenid(tokenid);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(infoMap);
	}
	
	@RequestMapping(value="/checkpath",method=RequestMethod.GET)
	public void checkPath(String userpath) throws UnsupportedEncodingException, GlobalErrorInfoException{
		if(userpath == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Userinfo userinfo = null;
		try{
			userinfo = userinfoServiceImpl.selectByUserpath(userpath);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/user/updateuserinfo",method=RequestMethod.POST)
	public @ResponseBody Map updateUserinfo(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map userinfo = (Map) req.getAttribute("jsoninfo");
		String token_id = userinfo.get("token_id").toString();
		String user_name = userinfo.get("user_name").toString();
		String user_sex = userinfo.get("user_sex").toString();
		String user_path = userinfo.get("user_path").toString();
		if(token_id == null || "".equals(token_id)|| "null".equals(token_id)|| user_name == null || "".equals(user_name)|| "null".equals(user_name) || user_sex == null || "".equals(user_sex)|| "null".equals(user_sex) || user_path == null || "".equals(user_path)|| "null".equals(user_path)  ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try{
			userinfo = userinfoServiceImpl.updateUserinfo(userinfo);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/user/updateuseremail",method=RequestMethod.POST)
	public void updateUserEmail(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map infoMap = (Map) req.getAttribute("jsoninfo");
		String token_id = infoMap.get("token_id").toString();
		String user_email = infoMap.get("user_email").toString();
		String user_password = infoMap.get("user_password").toString();
		if(token_id == null || "".equals(token_id)|| "null".equals(token_id)||user_email == null || "".equals(user_email)|| "null".equals(user_email) || user_password == null || "".equals(user_password)|| "null".equals(user_password) ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try{
			infoMap = userinfoServiceImpl.updateUseremail(infoMap);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/user/updateuserpass",method=RequestMethod.POST)
	public void updateUserPass(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map infoMap = (Map) req.getAttribute("jsoninfo");
		try{
			userinfoServiceImpl.updateUserpass(infoMap);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/findpass",method=RequestMethod.GET)
	public @ResponseBody Map findPass(String email) throws GlobalErrorInfoException{
		if(email == null || "".equals(email)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Map infoMap = null;
		Userinfo userinfo;
		try {
			userinfo = userinfoServiceImpl.selectByEmail(email);
			if(userinfo == null){
				infoMap = new HashMap<>();
				infoMap.put("code", "6");
				infoMap.put("message", "email不存在");
				return infoMap;
			}
			Userlogin userlogin = userinfoServiceImpl.insertLogin(userinfo);
			SendMail.sendMailSSL(email, userinfo.getUser_name(), userlogin.getToken_id());
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/user/updatefindpass",method=RequestMethod.POST)
	public @ResponseBody Map updatefindPass(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map jsonmap = (Map) req.getAttribute("jsoninfo");
		String token_id = (String) jsonmap.get("token_id");
		String user_password_new = (String) jsonmap.get("user_password_new");
		String user_password_old = (String) jsonmap.get("user_password_old");
		if(token_id == null || "".equals(token_id) || "null".equals(token_id) || user_password_new == null || "".equals(user_password_new) || "null".equals(user_password_new) || user_password_old == null || "".equals(user_password_old) || "null".equals(user_password_old)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			userinfoServiceImpl.updateUserpass(jsonmap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
}
