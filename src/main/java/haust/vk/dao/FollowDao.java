package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Followinfo;

public interface FollowDao {

	public void insertFollow(Map followinfo) throws Exception;
	
	public void updateFollow(Map map) throws Exception;
	
	public void deleteFollow(String followid) throws Exception;
	
	public List<Map> selectFollowNotifyByUserid(String userid) throws Exception;
	
	public List<Map> selectFollowListByUserid(String userid) throws Exception;

	public List<Map> selectFollowListByUseridAndfollowUserid(Map map) throws Exception;

}
