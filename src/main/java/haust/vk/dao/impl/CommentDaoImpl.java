package haust.vk.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import haust.vk.dao.CommentDao;
import haust.vk.entity.Commentinfo;

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
	public void selectCommentNotifyByUserid(String userid) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectCommentListByUserid(String userid) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
