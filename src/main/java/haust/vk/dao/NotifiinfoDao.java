package haust.vk.dao;

import java.util.Map;

public interface NotifiinfoDao {

	Map selectNotifiByUserid(String user_id) throws Exception;
	
	void upadteReadTime(Map map) throws Exception;
}
