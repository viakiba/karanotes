package haust.vk.service;

import java.util.Map;

import haust.vk.entity.Userinfo;

public interface UserinfoService {
	
	/**
	 * 注册
	 * @param userinfoMap
	 */
	public boolean registerUser(Map userinfoMap);
	
	/**
	 * 登录
	 * @param userinfoMap
	 * @return
	 */
	public Userinfo loginUser(Map userinfoMap);
}
