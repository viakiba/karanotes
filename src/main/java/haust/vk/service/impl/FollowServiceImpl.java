package haust.vk.service.impl;

import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.FollowDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;
import haust.vk.exception.GlobalErrorInfoException;
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
		Integer is_eachother = Integer.valueOf( (String) jsoninfo.get("is_eachother"));
		String userid = (String) jsoninfo.get("user_id");
		//添加关注标识  0 无关注  1 被检索的用户已经关注了用户   2用户已经关注了被检索到的用户   3已经互相关注
		System.out.println(is_eachother);
		if("3".equals(String.valueOf(is_eachother))){
			//更新
			Map map = new HashMap();
			map.put("user_id", follow_userid);
			map.put("follow_user_id", userid);
			map.put("is_eachother",0);
			followDaoImpl.updateFollow(map);
			followDaoImpl.deleteFollow(follow_id);
		}else{
			followDaoImpl.deleteFollow(follow_id);
		}
	}
	
	@Override
	public List<Map> selectFollowListByUserid(String tokenid) throws GlobalErrorInfoException,Exception {
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		if(userinfo == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		String user_id = userinfo.getUser_id();
		List<Map> listhelp = followDaoImpl.selectFollowListByUseridHelp(user_id);
		List<String> listuserid = new ArrayList<>();
		for (Map temp : listhelp) {
			listuserid.add((String) temp.get("user_id"));
		}
		List<Map> list = followDaoImpl.selectFollowListByUserid(listuserid);
		for (Map map : list) {
			String temp_user_id = (String) map.get("user_id");
			for (Map map2 : listhelp) {
				if( temp_user_id.equals( (String) map2.get("user_id") ) ){
					String is_eachother = String.valueOf(map2.get("is_eachother"));
					System.out.println(is_eachother+"***************");
					if(is_eachother.equals("true")){
						map.put("is_eachother", "3");
					}else{
						map.put("is_eachother", "2");
					}
					map.remove("user_password");
					break;
				}
			}
		}
		return list;
	}
	
	@Override
	public List<Map> getFollowNotifyByUserid(String tokenid) throws Exception {
		Userinfo userinfo = userLoginDaoImpl.selectUserloginByTokenid(tokenid);
		if(userinfo == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		String user_id = userinfo.getUser_id();
		List<String> listHelp = followDaoImpl.selectFollowNotifyByUseridHelp(user_id);
		for (String string : listHelp) {
			System.out.println("***************************");
			System.out.println(string);
			System.out.println("***************************");
		}
		List<Map> list = followDaoImpl.selectFollowNotifyByUserid(listHelp);
		followDaoImpl.updateNotifiinfo(user_id);
		for (Map map : list) {
			map.remove("user_password");
		}
		return list;
	}
}
