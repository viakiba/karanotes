package haust.vk.dao;

import haust.vk.entity.Articlepraise;

public interface PraiseDao {
	/**
	 * 添加点赞
	 * @param praiseInfo
	 * @throws Exception
	 */
	public void insertPraise(Articlepraise praiseinfo) throws Exception;

	/**
	 * 删除点赞
	 * @param praiseid
	 * @throws Exception
	 */
	public void deletePraise(String praiseid) throws Exception;
	
	/**
	 * 查询点赞通知
	 * @param userid
	 * @throws Exception
	 */
	public void selectPraiseNotifyByUserid(String userid) throws Exception;
	
	/**
	 * 查看点赞
	 * @param userid
	 * @throws Exception
	 */
	public void selectPraiseListByUserid(String userid) throws Exception;
}
