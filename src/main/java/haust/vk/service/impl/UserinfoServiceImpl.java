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
		
		if( user_email == null | user_password == null){
			userinfoMap.clear();
			userinfoMap.put("success", -1);
			userinfoMap.put("messcode", 4);
			return userinfoMap;
		}
		//先进行判断邮箱是否重复
		List<Userinfo> userlist = userinfoDaoImpl.selectUserByEmail(user_email);
		if(userlist.size() >0 ){
			System.out.println("邮箱已经存在");
			return null;
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
		userinfo.put("success", 1);
		userinfo.put("messcode", 2);
		return userinfo;
	}

	/**
	 * 登录
	 */
	@Override
	public Map loginUser(Map userinfoMap) throws Exception{
		String user_email = userinfoMap.get("user_email").toString();
		String user_password = userinfoMap.get("user_password").toString();
		
		if(user_email == null | user_password == null){
			userinfoMap.clear();
			userinfoMap.put("messcode", 4);
			userinfoMap.put("success", -1);
			return userinfoMap;
		}
		
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
		
		userinfoMap.put("messcode",2);
		userinfoMap.put("success",1);
		
		return userinfoMap;
	}
	
	/**
	 * 检查email唯一性
	 */
	@Override
	public Map selectByEmail(String email) throws Exception {
		Map map = new HashMap();
		List<Userinfo> userlist = userinfoDaoImpl.selectUserByEmail(email);
		if(userlist == null | userlist.size() == 0){
			map.put("success", -1);
			map.put("messcode", 1);
			return map;
		}
		Userinfo userinfo = userlist.get(0);
		map.put("success", 1);
		map.put("messcode", 2);
		map.put("user_name", userinfo.getUser_name());
		return map;
	}
	
	/**
	 * 检查token_id有效性
	 */
	@Override
	public Map selectUserloginByTokenid(String token_id) throws Exception {
		Map map = new HashMap();
		
		if(token_id == null | token_id.length() == 0){
			map.put("success", -1);
			map.put("messcode", 1);
			return map;
		}
		
		Userlogin userlogin = userloginDaoImpl.selectUserloginByTokenid(token_id);
		if(userlogin == null ){
			map.put("success", -1);
			map.put("messcode", 1);
			return map;
		}
		
		map.put("success", 1);
		map.put("messcode", 2);
		map.put("failtime", Integer.valueOf(userlogin.getUser_login_time())+15*24*60*60*1000);
		return map;
	}
	
	/**
	 * 检查user_path唯一性
	 */
	@Override
	public Map selectByUserpath(String user_path) throws Exception {
		Map map = new HashMap();;
		if(user_path == null){
			map.put("success", -1);
			map.put("messcode", 4);
			return map;
		}
		
		Userinfo user_name = userinfoDaoImpl.selectByUserpath(user_path);
		
		if(user_name == null){
			map.put("success", 1);
			map.put("messcode", 2);
			return map;
		}
		
		map.put("success", -1);
		map.put("messcode", 1);
		map.put("user_name", user_name.getUser_name());
		
		return map;
	}

	@Override
	public Map updateUserinfo(String userinfo) throws Exception {
		
		return null;
	}


	
}
