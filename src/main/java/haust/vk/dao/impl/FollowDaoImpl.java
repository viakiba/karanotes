package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.FollowDao;
import haust.vk.entity.Followinfo;

@Repository
public class FollowDaoImpl implements FollowDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertFollow(Map followinfo) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertFollow", followinfo);
		os.close();
	}
	
	@Override
	public void updateFollow(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateFollow", map);
		os.close();
	}

	@Override
	public void deleteFollow(String followid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteFollow",followid);
		os.close();
	}

	@Override
	public List<Map> selectFollowNotifyByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowNotifyByUserid", userid);
		os.close();
		return list;
	}

	@Override
	public List<Map> selectFollowListByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowListByUserid", userid);
		os.close();
		return list;
	}
	
	@Override
	public List<Map> selectFollowListByUseridAndfollowUserid(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowListByUseridAndfollowUserid", map);
		os.close();
		return list;
	}
}
