package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Followinfo;

public interface FollowDao {

	/**
	 * 添加关注
	 * @param commentInfo
	 * @throws Exception
	 */
	public void insertFollow(Followinfo followinfo) throws Exception;

	/**
	 * 删除关注
	 * @param followid
	 * @throws Exception
	 */
	public void deleteFollow(String followid) throws Exception;
	
	/**
	 * 查询关注通知
	 * @param userid
	 * @throws Exception
	 */
	public List<Map> selectFollowNotifyByUserid(String userid) throws Exception;
	
	/**
	 * 查看关注
	 * @param userid
	 * @throws Exception
	 */
	public List<Map> selectFollowListByUserid(String userid) throws Exception;
}
