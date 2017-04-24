package haust.vk.dao.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.NotifiinfoDao;

@Repository
public class NotifiinfoDaoImpl implements NotifiinfoDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public Map selectNotifiByUserid(String user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		Map selectOne = os.selectOne("selectNotifiByUserid", user_id);
		os.close();
		return selectOne;
	}
	
	@Override
	public void upadteReadTime(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.selectOne("upadteReadTime", map);
		os.close();
	}
}
