package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import haust.vk.dao.ClassifyDao;
import haust.vk.entity.Articleclassify;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ClassifyDaoImpl implements ClassifyDao{
	@Resource
	private SqlSessionFactory sqlSessionFactory;

	@Override
	public void insertClassify(Articleclassify articleclassify) {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertClassify", articleclassify);
		os.close();
	}

	@Override
	public void deleteClassify(String classify_id) {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteClassify", classify_id);
		os.close();
	}

	@Override
	public List<Articleclassify> getallClassify(Map userinfo) {
		SqlSession os = sqlSessionFactory.openSession();
		List<Articleclassify> list = os.selectList("getallClassify", userinfo);
		os.close();
		return list;
	}

	@Override
	public void updateClassify(Articleclassify articleclassify) {
		SqlSession os = sqlSessionFactory.openSession();
		os.update("updateClassify", articleclassify);
		os.close();
	}

	@Override
	public String getClassifyByContentAndUserid(Map userinfo) {
		SqlSession os = sqlSessionFactory.openSession();
		String classifyContent = os.selectOne("getClassifyByContent", userinfo);
		os.close();
		return classifyContent;
	}
	
}
