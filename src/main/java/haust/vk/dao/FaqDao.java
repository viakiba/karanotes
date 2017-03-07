package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Faqinfo;

public interface FaqDao {
	/**
	 * 增加
	 */
	public void insertFaq(Faqinfo faqinfo) throws Exception;
	
	/**
	 * 查看  分页
	 */
	public List<Map> selectFaqList(Map map) throws Exception;

}
