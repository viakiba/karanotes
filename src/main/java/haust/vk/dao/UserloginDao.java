package haust.vk.dao;

import haust.vk.entity.Userinfo;
import haust.vk.entity.Userlogin;

public interface UserloginDao {
	
	public void insertUserlogin(Userlogin userlogin) throws Exception;
	
	public String selectUseridByTokenid(String token_id) throws Exception;

	public Userinfo selectUserloginByTokenid(String token_id) throws Exception;

}
