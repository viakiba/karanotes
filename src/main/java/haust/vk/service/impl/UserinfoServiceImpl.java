package haust.vk.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.UserinfoDao;
import haust.vk.entity.Userinfo;
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
	
	@Override
	public boolean registerUser(Map userinfoMap) {
		System.out.println("--------------");
		String user_email = (String) userinfoMap.get("user_email");
		System.out.println(user_email);
		System.out.println(userinfoMap.get("user_password"));
		//先进行判断邮箱是否重复
		List<Userinfo> userlist = userinfoDaoImpl.selectUserByEmail(user_email);
		if(userlist.size() >0 ){
			System.out.println("邮箱已经存在");
			return false;
		}
		//处理用户ID
		long nextid = snowflakeIdUtil.nextId();
		userinfoMap.put("user_id", nextid);
		
		//处理密码
		String user_password = (String) userinfoMap.get("user_password");
		String encrypt = encryptUtil.MD5Encode(user_password);
		userinfoMap.remove("user_password");
		userinfoMap.put("user_password",encrypt);
		
		//处理用户名
		userinfoMap.put("user_name",user_email);
		
		//处理博客路径
		System.out.println(user_email.split("@")[0]);
		userinfoMap.put("user_path",user_email.split("@")[0]+UUID.randomUUID().toString().replace("-", "").substring(24));
		
		//设置用户信息 邮箱 密码 昵称 博客路径
		userinfoDaoImpl.registerUserInfo(userinfoMap);
		
		return true;
	}

	@Override
	public Userinfo loginUser(Map userinfoMap) {
		String user_password =(String) userinfoMap.get("user_password");
		user_password = encryptUtil.MD5Encode(user_password);
		userinfoMap.remove("user_password");
		userinfoMap.put("user_password", user_password);
		List<Userinfo> loginUserInfo = userinfoDaoImpl.loginUserInfo(userinfoMap);
		if(loginUserInfo.size() > 0){
			return loginUserInfo.get(0);
		}
			return null;
	}

	
	
}
