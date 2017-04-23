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
	
	/**
	 * 更改用户头像/背景图
	 * @param user_path
	 * @return
	 * @throws Exception
	 */
	public void updateUserimg(Map img) throws Exception;
	
	/**
	 * 更改用户基本信息
	 * @param user
	 * @throws Exception
	 */
	public void updateUserBaseinfo(Userinfo user) throws Exception;
	
	/**
	 * 更改用户邮箱
	 * @param user
	 * @throws Exception
	 */
	public int updateUseremail(Map infoMap) throws Exception;
	
	/**
	 * 更改用户密码
	 * @param infoMap
	 * @throws Exception
	 */
	public int updateUserpass(Map infoMap) throws Exception;

	public List<Map> selectUserList(Map infomap) throws Exception;

	public String selectCount(Map infomap) throws Exception;

	public int setPasswordByUserid(Map map) throws Exception;

}
