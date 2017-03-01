package haust.vk.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.CommentDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Commentinfo;
import haust.vk.service.CommentService;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private UserloginDao userloginDaoImpl; 
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil; 
	
	@Resource
	private CommentDao commentDaoImpl;
	
	@Override
	public Map insertComment(Map commentMap) {
		String token_id = commentMap.get("token_id").toString();
		String article_id = commentMap.get("article_id").toString();
		String comment_content = commentMap.get("comment_content").toString();
		if( token_id == null | article_id == null | comment_content == null){
			commentMap.clear();
			commentMap.put("success", -1);
			commentMap.put("messcode", 4);
			return commentMap;
		}
		
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if(user_id == null){
			commentMap.clear();
			commentMap.put("success", -1);
			commentMap.put("messcode", 1);
			return commentMap;
		}
		
		String reply_id = commentMap.get("reply_id").toString();
		String comment_id = String.valueOf( snowflakeIdUtil.nextId() );
		//放入评论
		Commentinfo commentinfo = new Commentinfo();
		commentinfo.setComment_id(comment_id);
		commentinfo.setUser_id(user_id);
		commentinfo.setComment_content(comment_content);
		commentinfo.setArticle_id(article_id);
		commentinfo.setReply_id(reply_id);
		
		try {
			commentDaoImpl.insertComment(commentinfo);
		} catch (Exception e) {
			e.printStackTrace();
			commentMap.clear();
			commentMap.put("success", -1);
			commentMap.put("messcode", "5 不可预知的错误");
			return commentMap;
		}
		
		commentMap.clear();
		commentMap.put("success", 1);
		commentMap.put("messcode", 2);
		commentMap.put("comment_id", comment_id);
		return commentMap;
	}

	@Override
	public Map deleteComment(Map commentMap) throws Exception {
		String token_id = commentMap.get("token_id").toString();
		String comment_id = commentMap.get("comment_id").toString();
		if(comment_id == null | token_id == null){
			commentMap.clear();
			commentMap.put("success", 1);
			commentMap.put("messcode", 4);
			return commentMap;
		}
		
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if(user_id == null){
			commentMap.clear();
			commentMap.put("success", -1);
			commentMap.put("messcode", 1);
			return commentMap;
		}
		
		try {
			commentDaoImpl.deleteComment(comment_id);
		} catch (Exception e) {
			e.printStackTrace();
			commentMap.clear();
			commentMap.put("success", -1);
			commentMap.put("messcode", "5 不可预知的错误");
			return commentMap;
		}
		
		commentMap.clear();
		commentMap.put("success", 1);
		commentMap.put("messcode", 2);
		return commentMap;
	}

}
