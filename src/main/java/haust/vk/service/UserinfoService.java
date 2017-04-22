package haust.vk.service;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;

public interface UserinfoService {
	
	public Map registerUser(Map userinfoMap) throws Exception;
	
	public Map loginUser(Map userinfoMap) throws Exception;
	
	public Userlogin insertLogin(Userinfo userinfo) throws Exception;
	
	public Userinfo selectByEmail(String email) throws Exception;

	public Userinfo selectUserinfoByTokenid(String token_id) throws Exception;
	
	public Userinfo selectByUserpath(String user_path) throws Exception;

	public Map updateUserinfo(Map infoMap) throws Exception;
	
	public Map updateUseremail(Map infoMap) throws Exception;

	public void updateUserpass(Map infoMap) throws Exception;

	public void updateUserlogo(Map map) throws Exception;

	public void updateUserBacklogo(Map map) throws Exception;

	public List<Map> selectUserListByTokenid(Map infoMap) throws Exception;

	public List<Map> selectUserList(Map infoMap) throws Exception;
}
