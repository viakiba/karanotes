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
	public void deleteCollect(Map collectid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteCollect",collectid);
		os.close();
	}

	@Override
	public int selectCollectNotifyByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		int i = os.selectOne("selectCollectNotifyByUserid", userid);
		os.close();
		return i;
	}

	@Override
	public List<Map> selectCollectListByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectCollectListByUserid", userid);
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
	public List<Map> selectPraiseNotifi(Map collectMap) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectCollectListByUserid", collectMap);
		os.close();
		return list;
	}
}
