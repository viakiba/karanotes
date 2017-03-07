package haust.vk.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import haust.vk.dao.CommentDao;
import haust.vk.entity.Commentinfo;

@Repository
public class CommentDaoImpl implements CommentDao{
	
	@Resource
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void insertComment(Commentinfo commentInfo) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.insert("insertComment", commentInfo);
		os.close();
	}

	@Override
	public void deleteComment(String comment_id) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		os.delete("deleteComment", comment_id);
		os.close();
		
	}

	@Override
	public List<Map> selectCommentNotifyByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> mapList = os.selectList("selectCommentNotifyByUserid", userid);
		os.close();
		return mapList;
	}

	@Override
	public List<Map> selectCommentListByUserid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> mapList = os.selectList("selectCommentListByUserid", userid);
		os.close();
		return mapList;
	}

}
