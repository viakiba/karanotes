package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Faqinfo;

public interface FaqDao {

	public void insertFaq(Map faqinfo) throws Exception;
	
	public List<Map> selectFaqList(Map map) throws Exception;

	public String selectFaqCount() throws Exception;

	public void deleteFaqinfo(String faqid) throws Exception;

}
