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
	public void deleteFollow(Map followid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteFollow",followid);
		os.close();
	}
	
	@Override
	public List<String> selectFollowNotifyByUseridHelp(Map user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<String> list = os.selectList("selectFollowNotifyByUseridHelp", user_id);
		os.close();
		return list;
	}

	@Override
	public List<Map> selectFollowNotifyByUserid(List<String> list) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> listresult = os.selectList("selectFollowNotifyByUserid", list);
		os.close();
		return listresult;
	}
	
	@Override
	public List<Map> selectFollowListByUseridHelp(String user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowListByUseridHelp", user_id);
		os.close();
		return list;
	}
	@Override
	public List<Map> selectFollowListByUserid(List<String> listuserid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowListByUserid", listuserid);
		os.close();
		return list;
	}
	
	@Override
	public void updateNotifiinfo(String user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateNotifiinfo",user_id);
		os.close();
	}
	
	@Override
	public List<Map> selectFollowListByUseridAndfollowUserid(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowListByUseridAndfollowUserid", map);
		os.close();
		return list;
	}
	
	@Override
	public List<Map> selectFansList(Map user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFansList", user_id);
		os.close();
		return list;
	}
	
	@Override
	public List<Map> selectFollowList(Map user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectFollowList", user_id);
		os.close();
		return list;
	}
	
	@Override
	public List<String> selectFollowNotifyByUseridHelp2(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<String> list = os.selectList("selectFollowNotifyByUseridHelp2", map);
		os.close();
		return list;
	}
}
