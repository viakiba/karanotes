package haust.vk.service;

import java.util.Map;

import haust.vk.entity.Userinfo;

public interface UserinfoService {
	
	/**
	 * 注册
	 * @param userinfoMap
	 * @return
	 */
	public Map registerUser(Map userinfoMap);
	
	/**
	 * 登录
	 * @param userinfoMap
	 * @return
	 */
	public Map loginUser(Map userinfoMap);
}
