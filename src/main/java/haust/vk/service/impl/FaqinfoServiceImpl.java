package haust.vk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.FaqDao;
import haust.vk.service.FaqinfoService;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class FaqinfoServiceImpl implements FaqinfoService{
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private FaqDao faqDaoImpl;
	
	@Override
	public Map selectFromFaqinfo(Map map) throws Exception {
		Map result = new HashMap<>();
		List<Map> list = faqDaoImpl.selectFaqList(map);
		String count = faqDaoImpl.selectFaqCount();
		result.put("faqlist", list);
		result.put("count", count);
		return result;
	}
	
	@Override
	public void insertFaq(Map jsoninfo) throws Exception {
		long nextId = snowflakeIdUtil.nextId();
		jsoninfo.put("faq_id", String.valueOf(nextId));
		faqDaoImpl.insertFaq(jsoninfo);
	}
	
	@Override
	public void deleteFaqinfo(String faqid) throws Exception {
		faqDaoImpl.deleteFaqinfo(faqid);
	}
}
