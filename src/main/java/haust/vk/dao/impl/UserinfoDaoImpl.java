package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.UserinfoDao;
import haust.vk.entity.Userinfo;

@Repository
public class UserinfoDaoImpl implements UserinfoDao{
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public void registerUserInfo(Map userinfomap) {
		SqlSession openSession = sqlSessionFactory.openSession();
		openSession.insert("registerUserInfo", userinfomap);
		openSession.close();
	}

	@Override
	public List<Userinfo> loginUserInfo(Map userinfomap) {
		SqlSession openSession = sqlSessionFactory.openSession();
		List<Userinfo> userList = openSession.selectList("loginUserInfo",userinfomap);
		openSession.close();
		return userList;
	}

	@Override
	public List<Userinfo> selectUserByEmail(String user_email) {
		SqlSession openSession = sqlSessionFactory.openSession();
		System.out.println(user_email);
		List<Userinfo> userList = openSession.selectList("selectUserByEmail",user_email);
		openSession.close();
		return userList;
	}
	
	
}
