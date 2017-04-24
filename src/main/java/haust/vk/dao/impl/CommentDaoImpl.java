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
	public void insertComment(Map commentInfo) throws Exception {
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
	public int selectAllcommentNotifiNum(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		Integer count = os.selectOne("selectAllcommentNotifiNum", map);
		os.close();
		return count;
	}

	@Override
	public List<Map> selectAllCommentByArticleid(String userid) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> mapList = os.selectList("selectAllCommentByArticleid", userid);
		os.close();
		return mapList;
	}
	
	@Override
	public List<Map> selectAllcommentNotifi(Map map) throws Exception {
		SqlSession os = sqlSessionFactory.openSession();
		List<Map> mapList = os.selectList("selectAllcommentNotifi", map);
		os.close();
		return mapList;
	}
}
