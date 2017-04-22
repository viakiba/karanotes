package haust.vk.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


import haust.vk.dao.FollowDao;
import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.service.UserinfoService;
import haust.vk.utils.EncryptUtil;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class UserinfoServiceImpl implements UserinfoService{
	
	private static Logger logger = Logger.getLogger(UserinfoServiceImpl.class);
	@Resource
	private UserinfoDao userinfoDaoImpl;
	@Resource
	private EncryptUtil encryptUtil;
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private UserloginDao userloginDaoImpl;
	@Resource
	private FollowDao followDaoImpl;
	
	/**
	 * 注册
	 */
	@Override
	public Map registerUser(Map userinfoMap) throws Exception{
		System.out.println("--------------");
		String user_email = (String) userinfoMap.get("user_email");
		String user_password = (String) userinfoMap.get("user_password");
		
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
		
		//处理用户图像
		userinfoMap.put("user_headimg", "2.png");
		userinfoMap.put("user_background_img", "1.png");

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
		String user_email = (String) userinfoMap.get("user_email");
		String user_password = (String) userinfoMap.get("user_password");
		
		user_password = encryptUtil.MD5Encode(user_password);
		userinfoMap.remove("user_password");
		userinfoMap.put("user_password", user_password);
		List<Userinfo> loginUserInfo = userinfoDaoImpl.loginUserInfo(userinfoMap);
		
		if(loginUserInfo == null | loginUserInfo.size() <= 0  ){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		Userinfo userinfo = loginUserInfo.get(0);
		//设置登陆的id
		long token_id = snowflakeIdUtil.nextId();
		//放到数据库中  
		Userlogin userlogin = new Userlogin();
		userlogin.setToken_id(String.valueOf(token_id));
		userlogin.setUser_id(userinfo.getUser_id());
		userlogin.setUser_login_time(String.valueOf( new Timestamp( (new Date()).getTime()) ));
		userloginDaoImpl.insertUserlogin(userlogin);
		
		userinfoMap.clear();
		userinfoMap.put("token_id", String.valueOf(token_id));
		userinfoMap.put("user_id", String.valueOf(userinfo.getUser_id()));
		userinfoMap.put("user_headimg", userinfo.getUser_headimg());
		userinfoMap.put("user_background_img", userinfo.getUser_background_img());
		userinfoMap.put("user_email", userinfo.getUser_email());
		userinfoMap.put("user_sex", userinfo.getUser_sex());
		userinfoMap.put("user_path", userinfo.getUser_path());
		userinfoMap.put("user_signature", userinfo.getUser_signature());
		userinfoMap.put("user_name", userinfo.getUser_name());
		userinfoMap.put("user_github", userinfo.getUser_github());
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
	public Userinfo selectUserinfoByTokenid(String token_id) throws Exception {
		Userinfo userinfo = userloginDaoImpl.selectUserloginByTokenid(token_id);
		if(userinfo == null ){
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
		}
		return userinfo;
	}
	
	@Override
	public Userinfo selectByUserpath(String user_path) throws Exception {
		Map map = new HashMap();
		Userinfo userinfo = userinfoDaoImpl.selectByUserpath(user_path);
		return userinfo;
	}

	@Override
	public Map updateUserinfo(Map userinfo) throws Exception {
		String token_id = (String) userinfo.get("token_id");
		String user_name = (String) userinfo.get("user_name");
		String user_sex = (String) userinfo.get("user_sex");
		String user_path = (String) userinfo.get("user_path");
		String user_signature = (String) userinfo.get("user_signature");
		
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		
		if(user_id == null){
			logger.info(user_id);
			throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.FAIL_CODE_MESSAGE);
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
		String token_id = (String) infoMap.get("token_id");
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
		String user_password_old = (String) infoMap.get("user_password_old");
		String user_password_new = (String) infoMap.get("user_password_new");
		
		user_password_old = EncryptUtil.MD5Encode(user_password_old);
		user_password_new = EncryptUtil.MD5Encode(user_password_new);
		
		infoMap.put("user_password_old",user_password_old);
		infoMap.put("user_password_new",user_password_new);
		
		userinfoDaoImpl.updateUserpass(infoMap);
	}

	//用户列表
	@Override
	public List<Map> selectUserListByTokenid(Map infomap) throws Exception {
		String keyword = (String) infomap.get("keywords");
		if(keyword == null || "".equals(keyword) || "null".equals(keyword)){
			infomap.remove(keyword);
		}else if(keyword.contains("@")){
			infomap.put("is_email", "1");
		}
		String user_id = (String) infomap.get("user_id");
		List<Map> list = userinfoDaoImpl.selectUserList(infomap);
		List<String> listUserid = new ArrayList<String>();
		
		Map tempMap = null;
		for (Map temp : list) {
			String userid = (String) temp.get("user_id");
			listUserid.add(userid);
			temp.remove("user_password");
			if(user_id.equals( (String) temp.get("user_id"))){
				tempMap = temp;
			}
		}
		System.out.println(list.size());
		list.remove(tempMap);
		System.out.println(list.size());
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("listuser_id", listUserid);
		//第一步正查
		List<Map> listFollowPositive = followDaoImpl.selectFollowListByUseridAndfollowUserid(map);
		//第二步反查
		map.put("reverse", "1");
		List<Map> listFollowreverse = followDaoImpl.selectFollowListByUseridAndfollowUserid(map);
		//添加关注标识  0 无关注  1 被检索的用户已经关注了用户   2用户已经关注了被检索到的用户   3已经互相关注
		for (Map temp : list) {
			String userid = (String) temp.get("user_id");
			String is_eachother = "0";
			for(Map temp1 : listFollowreverse){
				String temp_user_id = (String) temp1.get("user_id");
				if(userid.equals(temp_user_id)){
					is_eachother = "1";
					break;
				}
			}
			for(Map temp1 : listFollowPositive){
				String temp_user_id = (String) temp1.get("follow_user_id");
				if(userid.equals(temp_user_id)){
					if(is_eachother.equals("0")){
						is_eachother = "2";
					}else{
						is_eachother = "3";
					}
					break;
				}
			}
			temp.put("is_eachother", is_eachother);
		}
		return list;
	}
	
	@Override
	public List<Map> selectUserList(Map infomap) throws Exception {
		String keyword = (String) infomap.get("keywords");
		if(keyword == null || "".equals(keyword) || "null".equals(keyword)){
			infomap.remove(keyword);
		}else if(keyword.contains("@")){
			infomap.put("is_email", "1");
		}
		List<Map> list = userinfoDaoImpl.selectUserList(infomap);
		for (Map map : list) {
			map.remove("user_password");
		}
		return list;
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
