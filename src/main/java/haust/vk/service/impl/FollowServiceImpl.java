package haust.vk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.FollowDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;
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
	
	@Override
	public void insertFollow(Map jsoninfo) throws Exception {
		
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		Integer is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother"));
		String userid = (String) jsoninfo.get("user_id");
		System.out.println(userid);
		//
		if(is_eachother == 1){
			//更新
			Map map = new HashMap();
			map.put("user_id", follow_userid);
			map.put("follow_user_id", userid);
			map.put("is_eachother",1);
			followDaoImpl.updateFollow(map);
		}
		long nextId = snowflakeIdUtil.nextId();
		jsoninfo.put("follow_id", String.valueOf(nextId));
		followDaoImpl.insertFollow(jsoninfo);
	}
	
	@Override
	public void deleteFollow(Map jsoninfo) throws Exception {
		
		String follow_userid = (String) jsoninfo.get("follow_user_id");
		String follow_id = (String) jsoninfo.get("follow_id");
		Integer is_eachother = (Integer) jsoninfo.get("is_eachother");
		String userid = (String) jsoninfo.get("userid");
		//添加关注标识  0 无关注  1 被检索的用户已经关注了用户   2用户已经关注了被检索到的用户   3已经互相关注
		if(is_eachother == 1){
			//更新
			Map map = new HashMap();
			map.put("user_id", follow_userid);
			map.put("follow_user_id", userid);
			map.put("is_eachother",0);
			followDaoImpl.updateFollow(map);
		}
		followDaoImpl.deleteFollow(follow_id);
	}
	
	@Override
	public List<Map> selectFollowListByUserid(String tokenid) throws Exception {
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		String user_id = userinfo.getUser_id();
		List<Map> list = followDaoImpl.selectFollowListByUserid(user_id);
		return list;
	}
	
	@Override
	public List<Map> selectFollowNotifyByUserid(String tokenid) throws Exception {
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		String user_id = userinfo.getUser_id();
		List<Map> list = followDaoImpl.selectFollowNotifyByUserid(user_id);
		return list;
	}
}
