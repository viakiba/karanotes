package haust.vk.dao;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Commentinfo;

public interface CommentDao {
	
	/**
	 * 添加评论
	 * @param commentInfo
	 * @throws Exception
	 */
	public void insertComment(Commentinfo commentInfo) throws Exception;

	/**
	 * 删除评论
	 * @param comment_id
	 * @throws Exception
	 */
	public void deleteComment(String comment_id) throws Exception;
	
	/**
	 * 查询评论通知
	 * @param userid
	 * @throws Exception
	 */
	public List<Map> selectCommentNotifyByUserid(String userid) throws Exception;
	
	/**
	 * 查看文章评论
	 * @param userid
	 * @throws Exception
	 */
	public List<Map> selectCommentListByUserid(String userid) throws Exception;
}
