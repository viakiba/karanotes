package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Followinfo;

public interface FollowDao {

	public void insertFollow(Map followinfo) throws Exception;
	
	public void updateFollow(Map map) throws Exception;
	
	public void deleteFollow(Map map) throws Exception;
	
	public List<Map> selectFollowNotifyByUserid(List<String> list) throws Exception;
	
	public List<Map> selectFollowListByUserid(List<String> listuserid) throws Exception;

	public List<Map> selectFollowListByUseridAndfollowUserid(Map map) throws Exception;

	public List<String> selectFollowNotifyByUseridHelp(Map user_id) throws Exception;

	public void updateNotifiinfo(String user_id) throws Exception;

	public List<Map> selectFollowListByUseridHelp(String user_id) throws Exception;

	public List<Map> selectFansList(Map map) throws Exception;

	public List<Map> selectFollowList(Map user_id) throws Exception;

}
