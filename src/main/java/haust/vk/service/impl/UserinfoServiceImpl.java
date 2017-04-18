package haust.vk.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.UserinfoService;
import haust.vk.utils.EncryptUtil;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class UserinfoServiceImpl implements UserinfoService{
	@Resource
	private UserinfoDao userinfoDaoImpl;
	@Resource
	private EncryptUtil encryptUtil;
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private UserloginDao userloginDaoImpl;
	
	/**
	 * 注册
	 */
	@Override
	public Map registerUser(Map userinfoMap) throws Exception{
		System.out.println("--------------");
		String user_email = userinfoMap.get("user_email").toString();
		String user_password = userinfoMap.get("user_password").toString();
		
		//先进行判断邮箱是否重复
		List<Userinfo> userlist = userinfoDaoImpl.selectUserByEmail(user_email);
		if(userlist.size() >0 ){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		
		//处理用户ID
		long user_id = snowflakeIdUtil.nextId();
		userinfoMap.put("user_id", user_id);
		
		//处理密码
		String encrypt = encryptUtil.MD5Encode(user_password);
		userinfoMap.remove("user_password");
		userinfoMap.put("user_password",encrypt);
		
		//处理用户名
		userinfoMap.put("user_name",user_email);
		
		//处理博客路径
		String user_path = user_email.split("@")[0]+UUID.randomUUID().toString().replace("-", "").substring(24);
		userinfoMap.put("user_path",user_path);
		
		//设置用户信息 邮箱 密码 昵称 博客路径
		userinfoDaoImpl.registerUserInfo(userinfoMap);
		
		//设置登陆的id
		long token_id = snowflakeIdUtil.nextId();
		//放到数据库中  
		Userlogin userlogin = new Userlogin();
		userlogin.setToken_id(String.valueOf(token_id));
		userlogin.setUser_id(String.valueOf(user_id));
		userlogin.setLogin_device_type( (String) userinfoMap.get("login_device_type"));
		userlogin.setUser_login_time(String.valueOf(new Timestamp( (new Date()).getTime())));
		userloginDaoImpl.insertUserlogin(userlogin);
		
		//返回响应的数据
		Map userinfo = new HashMap();
		userinfo.put("token_id", String.valueOf(token_id));
		userinfo.put("user_name", user_email);
		userinfo.put("user_path", user_path);
		return userinfo;
	}

	@Override
	public Map loginUser(Map userinfoMap) throws Exception{
		String user_email = userinfoMap.get("user_email").toString();
		String user_password = userinfoMap.get("user_password").toString();
		
		user_password = encryptUtil.MD5Encode(user_password);
		userinfoMap.remove("user_password");
		userinfoMap.put("user_password", user_password);
		List<Userinfo> loginUserInfo = userinfoDaoImpl.loginUserInfo(userinfoMap);
		
		if(loginUserInfo == null | loginUserInfo.size() <= 0  ){
			userinfoMap.clear();
			userinfoMap.put("messcode", 1);
			userinfoMap.put("success", -1);
			return userinfoMap;
		}
		
		//设置登陆的id
		long token_id = snowflakeIdUtil.nextId();
		//放到数据库中  
		Userlogin userlogin = new Userlogin();
		userlogin.setToken_id(String.valueOf(token_id));
		userlogin.setUser_id(loginUserInfo.get(0).getUser_id());
		userlogin.setUser_login_time(String.valueOf( new Timestamp( (new Date()).getTime()) ));
		userloginDaoImpl.insertUserlogin(userlogin);
		
		userinfoMap.clear();
		userinfoMap.put("token_id", String.valueOf(token_id));
		userinfoMap.put("user_headimg", loginUserInfo.get(0).getUser_headimg());
		userinfoMap.put("user_email", loginUserInfo.get(0).getUser_email());
		userinfoMap.put("user_sex", loginUserInfo.get(0).getUser_sex());
		userinfoMap.put("user_path", loginUserInfo.get(0).getUser_path());
		userinfoMap.put("user_signature", loginUserInfo.get(0).getUser_signature());
		userinfoMap.put("user_extra", loginUserInfo.get(0).getUser_extra());
		
		return userinfoMap;
	}
	
	@Override
	public Userlogin insertLogin(Userinfo userinfo) throws Exception{
		String user_email = userinfo.getUser_email();
		String user_password = userinfo.getUser_password();
		Map userinfoMap = new HashMap<>();
		userinfoMap.put("user_email", user_email);
		userinfoMap.put("user_password", user_password);
		List<Userinfo> loginUserInfo = userinfoDaoImpl.loginUserInfo(userinfoMap);
		//设置登陆的id
		long token_id = snowflakeIdUtil.nextId();
		//放到数据库中  
		Userlogin userlogin = new Userlogin();
		userlogin.setToken_id(String.valueOf(token_id));
		userlogin.setUser_id(loginUserInfo.get(0).getUser_id());
		userlogin.setUser_login_time(String.valueOf( new Timestamp( (new Date()).getTime()) ));
		userloginDaoImpl.insertUserlogin(userlogin);
		
		return userlogin;
	}
	
	@Override
	public Userinfo selectByEmail(String email) throws Exception {
		List<Userinfo> userlist = userinfoDaoImpl.selectUserByEmail(email);
		if(userlist.size() == 0 | userlist == null){
			return null;
		}
		return userlist.get(0);
	}
	
	@Override
	public Userinfo selectUserloginByTokenid(String token_id) throws Exception {
		Userinfo userlogin = userloginDaoImpl.selectUserloginByTokenid(token_id);
		if(userlogin == null ){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		return userlogin;
	}
	
	@Override
	public Userinfo selectByUserpath(String user_path) throws Exception {
		Map map = new HashMap();;
		Userinfo userinfo = userinfoDaoImpl.selectByUserpath(user_path);
		if(userinfo == null){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
		}
		return userinfo;
	}

	@Override
	public Map updateUserinfo(Map userinfo) throws Exception {
		String token_id = userinfo.get("token_id").toString();
		String user_name = userinfo.get("user_name").toString();
		String user_sex = userinfo.get("user_sex").toString();
		String user_path = userinfo.get("user_path").toString();
		String user_signature = userinfo.get("user_signature").toString();
		
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		
		if(user_id == null){
			userinfo.clear();
			userinfo.put("success", -1);
			userinfo.put("messcode", 1);
			return userinfo;
		}
		
		Userinfo user = new Userinfo();
		
		user.setUser_id(user_id);
		user.setUser_name(user_name);
		user.setUser_sex(user_sex);
		user.setUser_path(user_path);
		user.setUser_signature(user_signature);
		
		userinfoDaoImpl.updateUserBaseinfo(user);
		return null;
	}

	@Override
	public Map updateUseremail(Map infoMap) throws Exception {
		String token_id = infoMap.get("token_id").toString();
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if(user_id == null){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		infoMap.put("user_id", user_id);
		int i = userinfoDaoImpl.updateUseremail(infoMap);
		return null;
	}
	
	@Override
	public void updateUserpass(Map infoMap) throws Exception {
		String token_id = infoMap.get("token_id").toString();
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if(user_id == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		infoMap.put("user_id", user_id);
		userinfoDaoImpl.updateUserpass(infoMap);
	}

	@Override
	public void updateUserlogo(Map map) throws Exception{
		userinfoDaoImpl.updateUserimg(map);
	}
	
	@Override
	public void updateUserBacklogo(Map map) throws Exception{
		userinfoDaoImpl.updateUserimg(map);
	}
	
}
