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
	public void registerUserInfo(Map userinfomap) throws Exception{
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("registerUserInfo", userinfomap);
		os.close();
	}

	@Override
	public List<Userinfo> loginUserInfo(Map userinfomap) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Userinfo> userList = os.selectList("loginUserInfo",userinfomap);
		os.close();
		return userList;
	}

	@Override
	public List<Userinfo> selectUserByEmail(String user_email) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Userinfo> userList = os.selectList("selectUserByEmail",user_email);
		os.close();
		return userList;
	}

	@Override
	public Userinfo selectByUserpath(String user_path) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		Userinfo userinfo = os.selectOne("selectUserByUserpath",user_path);
		os.close();
		return userinfo;
	}

	@Override
	public void updateUserimg(Map img) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateUserimg", img);
		os.close();
	}

	@Override
	public void updateUserBaseinfo(Userinfo user) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateUserBaseinfo", user);
		os.close();
	}

	@Override
	public int updateUseremail(Map infoMap) {
		SqlSession os = sqlSessionFactory.openSession();
		int i = os.update("updateUseremail", infoMap);
		os.close();
		return i;
	}
	
	@Override
	public int updateUserpass(Map infoMap) {
		SqlSession os = sqlSessionFactory.openSession();
		int i = os.update("updateUserpass", infoMap);
		os.close();
		return i;
	}
	
	
}
