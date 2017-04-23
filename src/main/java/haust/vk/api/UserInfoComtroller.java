package haust.vk.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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
import haust.vk.utils.EncryptUtil;
import haust.vk.utils.SendMail;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class UserInfoComtroller {
	private static Logger logger = Logger.getLogger(UserInfoComtroller.class);
	@Resource
	private UserinfoService userinfoServiceImpl;
	
	@RequestMapping(value="/extra/user/checkemail",method=RequestMethod.GET)
	public ResultBody checkEmail(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		String email = req.getParameter("email");
		logger.info(email);
		Userinfo userinfo = null;
		try{
			userinfo = userinfoServiceImpl.selectByEmail(email);
		}catch(Exception e){
			logger.error("checkEmail", e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		logger.info(userinfo);
		if(userinfo == null){
			Map map = new HashMap<>();
			return new ResultBody(map);
		}else{
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
	}
	
	@RequestMapping(value="/select/user/register",method=RequestMethod.POST)
	public ResultBody registerUserinfo(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Map userinfoMap = (Map) req.getAttribute("jsoninfo");
		String user_email = (String) userinfoMap.get("user_email");
		String user_password = (String) userinfoMap.get("user_password");
		logger.info(user_email+"***"+user_password);
		if( user_email == null || "".equals(user_email) || "null".equals(user_email) || user_password == null || "".equals(user_password) || "null".equals(user_password)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			userinfoMap = userinfoServiceImpl.registerUser(userinfoMap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		return new ResultBody(userinfoMap);
	}
	
	@RequestMapping(value="/select/user/login",method=RequestMethod.POST)
	public ResultBody loginUserinfo(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Map userinfoMap = (Map) req.getAttribute("jsoninfo");
		String user_email = (String) userinfoMap.get("user_email");
		String user_password = (String) userinfoMap.get("user_password");
		logger.info(user_email+"***"+user_password);
		if( user_email == null || "".equals(user_email) || "null".equals(user_email) || user_password == null || "".equals(user_password) || "null".equals(user_password)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			userinfoMap = userinfoServiceImpl.loginUser(userinfoMap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		logger.info(userinfoMap);
		return new ResultBody(userinfoMap);
	}

	@RequestMapping(value="/user/updateuserinfo",method=RequestMethod.POST)
	public ResultBody updateUserinfo(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map userinfo = (Map) req.getAttribute("jsoninfo");
		logger.info(userinfo);
		
		String token_id = (String) userinfo.get("token_id");
		String user_name = (String) userinfo.get("user_name");
		String user_sex = (String) userinfo.get("user_sex");
		String user_path = (String) userinfo.get("user_path");
		if(token_id == null || "".equals(token_id)|| "null".equals(token_id)|| user_name == null || "".equals(user_name)|| "null".equals(user_name) || user_sex == null || "".equals(user_sex)|| "null".equals(user_sex) || user_path == null || "".equals(user_path)|| "null".equals(user_path)  ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try{
			userinfo = userinfoServiceImpl.updateUserinfo(userinfo);
		}catch(Exception e){
			logger.debug(e);
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		return new ResultBody(new HashMap<>());
	}
	
	@RequestMapping(value="/extra/checktoken",method=RequestMethod.GET)
	public ResultBody checkToken(String tokenid) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map map = null;
		if(tokenid == null | tokenid.length() == 0){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try{
			Userinfo userInfo = userinfoServiceImpl.selectUserinfoByTokenid(tokenid);
		}catch(Exception e){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		return new ResultBody(map);
	}
	
	@RequestMapping(value="/extra/checkpath",method=RequestMethod.GET)
	public ResultBody checkPath(String userpath) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map map = null;
		if(userpath == null){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Userinfo userinfo = null;
		try{
			userinfo = userinfoServiceImpl.selectByUserpath(userpath);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		if(userinfo == null){
			return new ResultBody(map);
		}else{
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
	}
	
	@RequestMapping(value="/user/updateuseremail",method=RequestMethod.POST)
	public ResultBody updateUserEmail(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map infoMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");

		String token_id = (String) infoMap.get("token_id");
		String user_email = (String) infoMap.get("user_email");
		
		if(token_id == null || "".equals(token_id)|| "null".equals(token_id)||user_email == null || "".equals(user_email)|| "null".equals(user_email)  ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		infoMap.put("user_id", userinfo.getUser_id());
		try{
			infoMap = userinfoServiceImpl.updateUseremail(infoMap);
		}catch(Exception e){
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		Map map = null;
		return new ResultBody(map);
	}
	
	//用户列表（带搜索）
	@RequestMapping(value="/select/user/list",method=RequestMethod.POST)
	public ResultBody userListNotokenid(HttpServletRequest req, HttpServletResponse resp) throws  GlobalErrorInfoException{
		Map infoMap = (Map) req.getAttribute("jsoninfo");
		String pagenum = (String) infoMap.get("pagenum");
		String pagesize = (String) infoMap.get("pagesize");
		if(pagenum == null || "".equals(pagenum)|| "null".equals(pagenum) || pagesize == null || "".equals(pagesize)|| "null".equals(pagesize)  ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try{
			infoMap.put("pagesize", Integer.valueOf(pagesize));
			infoMap.put("start", Integer.valueOf(pagenum)*Integer.valueOf(pagesize));
		}catch (NumberFormatException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		List<Map> list = null;
		
		String tokenid = (String) infoMap.get("token_id");
		if(tokenid == null || "".equals(tokenid) || "null".equals(tokenid) ){
			try{
				infoMap = userinfoServiceImpl.selectUserList(infoMap);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("/extra/userlist/{tokenid}", e);
				throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
			}
			return new ResultBody(infoMap);
		}else{
			try {
				Userinfo userinfo = userinfoServiceImpl.selectUserinfoByTokenid(tokenid);
				infoMap.put("user_id", userinfo.getUser_id());
				infoMap = userinfoServiceImpl.selectUserListByTokenid(infoMap);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("/extra/userlist/{tokenid}", e);
				throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
			}
			return new ResultBody(infoMap);
		}
	}
	
	@RequestMapping(value="/user/updateuserpass",method=RequestMethod.POST)
	public ResultBody updateUserPass(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, GlobalErrorInfoException{
		Map infoMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		infoMap.put("user_id", userinfo.getUser_id());
		
		String user_password_old = (String) infoMap.get("user_password_old");
		String user_password_new = (String) infoMap.get("user_password_new");
		
		if(user_password_old == null || "".equals(user_password_old)|| "null".equals(user_password_old)||user_password_new == null || "".equals(user_password_new)|| "null".equals(user_password_new)  ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		try{
			userinfoServiceImpl.updateUserpass(infoMap);
		}catch(Exception e){
			logger.error("/user/updateuserpass", e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		Map map = null;
		return new ResultBody(map);
	}
	
	@RequestMapping(value="/extra/user/findpass",method=RequestMethod.GET)
	public ResultBody findPass(String email) throws GlobalErrorInfoException{
		if(email == null || "".equals(email)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Map infoMap = null;
		Userinfo userinfo = null;
		try {
			userinfo = userinfoServiceImpl.selectByEmail(email);
			if(userinfo == null){
				throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
			}
			Userlogin userlogin = userinfoServiceImpl.insertLogin(userinfo);
			SendMail.sendEmail(email,userlogin.getToken_id(),userinfo.getUser_name());
		} catch (Exception e) {
			logger.error("/select/user/findpass", e);
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/select/user/updatefindpass",method=RequestMethod.POST)
	public ResultBody updatefindPass(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Map jsonmap = (Map) req.getAttribute("jsoninfo");
		String token_id = (String) jsonmap.get("token_id");
		String user_password = (String) jsonmap.get("user_password");
		if(token_id == null || "".equals(token_id) || "null".equals(token_id) || user_password == null || "".equals(user_password) || "null".equals(user_password) ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		jsonmap.put("user_password", EncryptUtil.MD5Encode( user_password ));
		try {
			jsonmap = userinfoServiceImpl.updateUserFindPass(jsonmap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(jsonmap);
	}
}
