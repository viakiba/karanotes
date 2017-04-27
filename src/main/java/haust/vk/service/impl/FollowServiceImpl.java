package haust.vk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.FollowDao;
import haust.vk.dao.NotifiinfoDao;
import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.dao.impl.UserinfoDaoImpl;
import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.FollowService;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class FollowServiceImpl implements FollowService{
	
	@Resource
	private FollowDao followDaoImpl;
	@Resource
	private UserloginDao userLoginDaoImpl;
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private UserinfoDao userinfoDaoImpl;
	@Resource
	private NotifiinfoDao notifiinfoDaoImpl;
	
	/**
	 * 添加关注
	 */
	@Override
	public void insertFollow(Map jsoninfo) throws Exception {
		
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		Integer is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother"));
		String userid = (String) jsoninfo.get("user_id");
		System.out.println(userid);
		//判断被关注着是否关注了自己
		if(is_eachother == 1){
			//如果关注 更新两者 is_eachother 为1
			Map map = new HashMap();
			map.put("user_id", userid);
			map.put("follow_user_id", follow_userid);
			map.put("is_eachother",1);
			followDaoImpl.updateFollow(map);
		}
		long nextId = snowflakeIdUtil.nextId();
		jsoninfo.put("follow_id", String.valueOf(nextId));
		followDaoImpl.insertFollow(jsoninfo);
	}
	
	/**
	 * 取消关注
	 */
	@Override
	public void deleteFollow(Map jsoninfo) throws Exception {
		
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		Integer is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother"));
		String userid = (String) jsoninfo.get("user_id");
		//添加关注标识  0 无关注  1 被检索的用户已经关注了用户   2用户已经关注了被检索到的用户   3已经互相关注
		System.out.println(is_eachother);
		Map map = new HashMap();
		map.put("follow_user_id", follow_userid);
		map.put("user_id", userid);
		map.put("is_eachother",0);
		if("3".equals(String.valueOf(is_eachother))){
			followDaoImpl.updateFollow(map);
			followDaoImpl.deleteFollow(map);
		}else{
			followDaoImpl.deleteFollow(map);
		}
	}
	
	/**
	 * 用户关注通知详情
	 */
	@Override
	public List<Map> selectFollowListByUserid(String tokenid) throws GlobalErrorInfoException,Exception {
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		if(userinfo == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		//获取用户的 user_id
		String user_id = userinfo.getUser_id();
		//获取用户的所有关注者
		List<Map> listhelp = followDaoImpl.selectFollowListByUseridHelp(user_id);
		//装载user_id(followinfo)
		List<String> listuserid = new ArrayList<>();
		for (Map temp : listhelp) {
			listuserid.add((String) temp.get("user_id"));
		}
		List<Map> list = new ArrayList<Map>();
		if(listuserid.size() > 0){
			//拿到user_id（followinfo） 的信息
			list = followDaoImpl.selectFollowListByUserid(listuserid);
			for (Map map : list) {
				String temp_user_id = (String) map.get("user_id");
				for (Map map2 : listhelp) {
					if( temp_user_id.equals( (String) map2.get("user_id") ) ){
						String is_eachother = String.valueOf(map2.get("is_eachother"));
						String follow_id = String.valueOf(map2.get("follow_id"));
						System.out.println(is_eachother+"***************");
						if(is_eachother.equals("true")){
							map.put("is_eachother", "3");
						}else{
							map.put("is_eachother", "2");
						}
						map.put("follow_id", follow_id);
						map.put("user_password", "");
						break;
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 新关注消息数提醒
	 */
	@Override
	public List<Map> getFollowNotifyByUserid(String tokenid) throws Exception {
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		if(userinfo == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		String user_id = userinfo.getUser_id();
		Map temp = new HashMap<>();
		temp.put("user_id",user_id);
		temp.put("follow_create_time", "1");
		temp.put("follow_read_time", "1");
		List<String> listHelp = followDaoImpl.selectFollowNotifyByUseridHelp(temp);
		for (String string : listHelp) {
			System.out.println("***************************");
			System.out.println(string);
			System.out.println("***************************");
		}
		if(listHelp.size() > 0){
			List<Map> list = followDaoImpl.selectFollowNotifyByUserid(listHelp);
			for (Map map : list) {
				map.remove("user_password");
			}
			notifiinfoDaoImpl.upadteReadTime(temp);
			return list;
		}
		notifiinfoDaoImpl.upadteReadTime(temp);
		return null;
		
	}
	
	/**
	 * 获取关注列表
	 */
	@Override
	public List<Map> getFollowList(Map map) throws Exception {
		String tokenid = (String) map.get("token_id");
		String pagenum = (String) map.get("pagenum");
		String pagesize = (String) map.get("pagesize");
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		if(userinfo == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		Integer start = 0;
		try{
			start = Integer.valueOf(pagenum) * Integer.valueOf(pagesize);
		}catch ( NumberFormatException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		String user_id = userinfo.getUser_id();
		map.clear();
		map.put("user_id", user_id);
		map.put("start", start);
		map.put("pagesize", Integer.valueOf(pagesize));
		List<Map> list = followDaoImpl.selectFollowList(map);
		for (Map temp : list) {
			Boolean i = (Boolean) temp.get("is_eachother");
			String userid = (String) temp.get("follow_user_id");
			Userinfo user = userinfoDaoImpl.selectUserByUserid(userid);
			temp.put("user_id", user.getUser_id());
			temp.put("user_path", user.getUser_path());
			temp.put("user_name", user.getUser_name());
			temp.put("user_headimg", user.getUser_headimg());
			if(i){
				temp.put("is_eachother", "3");
			}else{
				temp.put("is_eachother", "2");
			}
		}
		return list;
	}
}
