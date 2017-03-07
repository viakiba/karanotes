package haust.vk.dao;

import haust.vk.entity.Collectinfo;
import haust.vk.entity.Commentinfo;

public interface CollectDao {
	/**
	 * 添加收藏
	 * @param commentInfo
	 * @throws Exception
	 */
	public void insertCollect(Collectinfo collectinfo) throws Exception;

	/**
	 * 删除收藏
	 * @param Collect_id
	 * @throws Exception
	 */
	public void deleteCollect(String collectid) throws Exception;
	
	/**
	 * 查询收藏通知
	 * @param userid
	 * @throws Exception
	 */
	public void selectCollectNotifyByUserid(String userid) throws Exception;
	
	/**
	 * 查看文章收藏
	 * @param userid
	 * @throws Exception
	 */
	public void selectCollectListByUserid(String userid) throws Exception;
	
}
