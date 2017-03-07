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
	public void insertCollect(Collectinfo collectinfo) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertCollect",collectinfo);
		os.close();
	}

	@Override
	public void deleteCollect(String collectid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteCollect",collectid);
		os.close();
	}

	@Override
	public List<Map> selectCollectNotifyByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectCollectNotifyByUserid", userid);
		os.close();
		return list;
	}

	@Override
	public List<Map> selectCollectListByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectCollectListByUserid", userid);
		os.close();
		return list;
	}
	
}
