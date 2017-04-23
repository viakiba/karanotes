package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.FaqDao;
import haust.vk.entity.Faqinfo;

@Repository
public class FaqDaoImpl implements FaqDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertFaq(Map faqinfo) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertFaq", faqinfo);
		os.close();
	}

	@Override
	public List<Map> selectFaqList(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> faqList = os.selectList("selectFaqList", map);
		os.close();
		return faqList;
	}
	
	@Override
	public String selectFaqCount() throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		String count = os.selectOne("selectFaqCount");
		os.close();
		return count;
	}
	
	@Override
	public void deleteFaqinfo(String faqid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteFaqinfo",faqid);
		os.close();
	}

}
