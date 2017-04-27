package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.CollectDao;
import haust.vk.entity.Collectinfo;

@Repository
public class CollectDaoImpl implements CollectDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertCollect(Map collectinfo) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertCollect",collectinfo);
		os.close();
	}

	@Override
	public int deleteCollect(Map collectid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		int i = os.delete("deleteCollect",collectid);
		os.close();
		return i;
	}

	@Override
	public int selectCollectNotifyByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		int i = os.selectOne("selectCollectNotifyByUserid", userid);
		os.close();
		return i;
	}

	@Override
	public List<Map> selectCollectListByUserid(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectCollectListByUserid", map);
		os.close();
		return list;
	}
	
	@Override
	public int selectIsCollect(Map collectMap) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		int i = os.selectOne("selectIsCollect", collectMap);
		os.close();
		return i;
	}
	
	@Override
	public List<Map> selectCollectNotifi(Map collectMap) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectCollectNotifi", collectMap);
		os.close();
		return list;
	}
	
	@Override
	public void deleteCollectByArticleid(String article_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteCollectByArticleid",article_id);
		os.close();
	}
	
	@Override
	public Map selectCollectByUseridAndArticleid(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		map = os.selectOne("selectCollectByUseridAndArticleid", map);
		return map;
	}
}
