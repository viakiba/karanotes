package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Commentinfo;

public interface CommentDao {
	
	public void insertComment(Map commentInfo) throws Exception;

	public void deleteComment(String comment_id) throws Exception;
	
	public int selectAllcommentNotifiNum(Map map) throws Exception;
	
	public List<Map> selectAllCommentByArticleid(String userid) throws Exception;
	
	public List<Map> selectAllcommentNotifi(Map map) throws Exception;
}
