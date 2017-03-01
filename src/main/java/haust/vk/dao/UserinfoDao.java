package haust.vk.dao;

import haust.vk.entity.Userinfo;

import java.util.List;
import java.util.Map;

public interface UserinfoDao {
	
	/**
	 * 注册用户 
	 * @param userinfo
	 */
	public void registerUserInfo(Map userinfomap) throws Exception;
	
	/**
	 * 登录用户
	 * @param userinfomap
	 */
	public List<Userinfo> loginUserInfo(Map userinfomap) throws Exception;
	
	/**
	 * 通过邮箱查找用户
	 * @param userinfomap
	 * @return
	 */
	public List<Userinfo> selectUserByEmail(String user_email) throws Exception;

	/**
	 * 通过user_path查找用户
	 * @param user_path
	 * @return
	 * @throws Exception
	 */
	public Userinfo selectByUserpath(String user_path) throws Exception;
	
	
}
