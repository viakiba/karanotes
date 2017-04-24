package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import haust.vk.dao.PraiseDao;
import haust.vk.entity.Articlepraise;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PraiseDaoImpl implements PraiseDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertPraise(Map praiseinfo) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertPraise",praiseinfo);
		os.close();
	}

	@Override
	public void deletePraise(Map praiseid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deletePraise",praiseid);
		os.close();
	}

	@Override
	public List<Map> selectPraiseNotifyByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectPraiseNotifyByUserid", userid);
		os.close();
		return list;
	}

	@Override
	public List<Map> selectPraiseListByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> list = os.selectList("selectPraiseListByUserid", userid);
		os.close();
		return list;
	}
	
	@Override
	public int selectPraiseByUseridAndPraiseUserid(Map praiseMap) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		int is_praise = os.selectOne("selectPraiseByUseridAndPraiseUserid", praiseMap);
		os.close();
		return is_praise;
	}
	
	@Override
	public int selectPraiseNotifyNumByUserid(String user_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		int notifi_praise = os.selectOne("selectPraiseNotifyNumByUserid", user_id);
		os.close();
		return notifi_praise;
	}
	
	@Override
	public List<Map> selectAllPraise(Map praiseMap) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
