package haust.vk.dao;

import haust.vk.entity.Userlogin;

public interface UserloginDao {
	
	/**
	 * 增加记录
	 * @param userinfomap
	 * @return
	 */
	public void insertUserlogin(Userlogin userlogin);
	
	/**
	 * 查找用户id user_id
	 * @param token_id
	 * @return
	 */
	public String selectUseridByTokenid(String token_id);
	/**
	 * 查找userlogin
	 * @param token_id
	 * @return
	 */
	public Userlogin selectUserloginByTokenid(String token_id);
}
