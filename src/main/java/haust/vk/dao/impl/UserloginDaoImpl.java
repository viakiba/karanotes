package haust.vk.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userlogin;

@Repository
public class UserloginDaoImpl implements UserloginDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertUserlogin(Userlogin userlogin) {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertUserlogin", userlogin);
		os.close();
	}

	@Override
	public String selectUseridByTokenid(String token_id) {
		SqlSession os = sqlSessionFactory.openSession();
		String user_id = (String) os.selectOne("selectUseridByTokenid", token_id);
		os.close();
		return user_id;
	}
	
	@Override
	public Userlogin selectUserloginByTokenid(String token_id) {
		SqlSession os = sqlSessionFactory.openSession();
		Userlogin userlogin = (Userlogin) os.selectOne("selectUserloginByTokenid", token_id);
		os.close();
		return userlogin;
	}

}
