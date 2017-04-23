package haust.vk.service;

import java.util.Map;

public interface FaqinfoService {

	Map selectFromFaqinfo(Map map) throws Exception;
	void insertFaq(Map jsoninfo) throws Exception;
	void deleteFaqinfo(String faqid) throws Exception;

}
