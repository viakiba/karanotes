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
	
	@Override
	public Map registerUser(Map userinfoMap) {
		System.out.println("--------------");
		String user_email = (String) userinfoMap.get("user_email");
		System.out.println(user_email);
		System.out.println(userinfoMap.get("user_password"));
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
		String user_password = (String) userinfoMap.get("user_password");
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
		
		userinfo.put("token_id", token_id);
		userinfo.put("user_name", user_email);
		userinfo.put("user_path", user_path);
		
		return userinfo;
	}

	@Override
	public Map loginUser(Map userinfoMap) {
		String user_password =(String) userinfoMap.get("user_password");
		user_password = encryptUtil.MD5Encode(user_password);
		userinfoMap.remove("user_password");
		userinfoMap.put("user_password", user_password);
		List<Userinfo> loginUserInfo = userinfoDaoImpl.loginUserInfo(userinfoMap);
		if(loginUserInfo.size() > 0){
			//设置登陆的id
			long token_id = snowflakeIdUtil.nextId();
			//放到数据库中  
			Userlogin userlogin = new Userlogin();
			userlogin.setToken_id(String.valueOf(token_id));
			userlogin.setUser_id(loginUserInfo.get(0).getUser_id());
			userlogin.setLogin_device_type( (String) userinfoMap.get("login_device_type"));
			userlogin.setUser_login_time(String.valueOf( new Timestamp( (new Date()).getTime()) ));
			userloginDaoImpl.insertUserlogin(userlogin);
			
			Map userinfomap = new HashMap();
			userinfomap.put("token_id", token_id);
			userinfomap.put("user_headimg", loginUserInfo.get(0).getUser_headimg());
			userinfomap.put("user_email", loginUserInfo.get(0).getUser_email());
			userinfomap.put("user_sex", loginUserInfo.get(0).getUser_sex());
			userinfomap.put("user_path", loginUserInfo.get(0).getUser_path());
			userinfomap.put("user_signature", loginUserInfo.get(0).getUser_signature());
			userinfomap.put("user_extra", loginUserInfo.get(0).getUser_extra());
			
			return userinfomap;
		}
			return null;
	}

	
	
}
