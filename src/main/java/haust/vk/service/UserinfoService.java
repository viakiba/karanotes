package haust.vk.service;

import java.util.Map;

import haust.vk.entity.Userinfo;

public interface UserinfoService {
	
	/**
	 * 注册
	 * @param userinfoMap
	 * @return
	 * @throws Exception 
	 */
	public Map registerUser(Map userinfoMap) throws Exception;
	
	/**
	 * 登录
	 * @param userinfoMap
	 * @return
	 */
	public Map loginUser(Map userinfoMap) throws Exception;
	
	/**
	 * 查看用户邮箱是否被注册
	 * @param email
	 * @return
	 */
	public Map selectByEmail(String email) throws Exception;
}
