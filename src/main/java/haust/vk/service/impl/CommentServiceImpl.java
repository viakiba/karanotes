package haust.vk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.CommentDao;
import haust.vk.dao.NotifiinfoDao;
import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Commentinfo;
import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
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
	
	@Resource
	private ArticleDao articleDaoImpl;
	
	@Resource
	private UserinfoDao userinfoDaoImpl;
	
	@Resource
	private NotifiinfoDao notifiinfoDaoImpl;
	@Override
	public Map insertComment(Map commentMap) throws Exception {
		String token_id = (String) commentMap.get("token_id");
		String article_id = (String) commentMap.get("article_id");
		String author_id = (String) commentMap.get("author_id");
		String comment_content = (String) commentMap.get("comment_content");
		if( author_id == null || "".equals(author_id) || "null".equals(author_id) || token_id == null || "".equals(token_id) || "null".equals(token_id) || article_id == null || "".equals(article_id) || "null".equals(article_id)|| comment_content == null || "".equals(comment_content) || "null".equals(comment_content)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		String user_id = (String) commentMap.get("user_id");
		String reply_id = (String) commentMap.get("reply_id");
		
		String comment_id = String.valueOf( snowflakeIdUtil.nextId() );
		
		//放入评论
		commentMap.put("comment_id", comment_id);
		commentMap.put("user_id", user_id);
		
		commentDaoImpl.insertComment(commentMap);
		//获取articleabstract
		Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
		
		//得到 comment_num
		Integer comment_num = (Integer) selectArticleAbstract.get("comment_num");
		//更新comment_num
		commentMap.clear();
		commentMap.put("article_id", article_id);
		commentMap.put("comment_num", Integer.valueOf( comment_num ) + 1);
		articleDaoImpl.updateOneNum(commentMap);
		commentMap.put("comment_id", comment_id);
		return commentMap;
	}

	@Override
	public Map deleteComment(Map commentMap) throws Exception {
		String comment_id = (String) commentMap.get("comment_id");
		String article_id = (String) commentMap.get("article_id");
		if(comment_id == null || "".equals(comment_id) || "null".equals(comment_id) || article_id == null || "".equals(article_id) || "null".equals(article_id) ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		String user_id = (String) commentMap.get("user_id");
		commentDaoImpl.deleteComment(comment_id);
		//获取articleabstract
		Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
		//得到 comment_num
		Integer comment_num = (Integer) selectArticleAbstract.get("comment_num");
		//更新comment_num
		commentMap.clear();
		commentMap.put("article_id", article_id);
		commentMap.put("comment_num", comment_num  - 1);
		articleDaoImpl.updateOneNum(commentMap);
		return commentMap;
	}
	
	@Override
	public List<Map> selectAllComment(String article_id) throws Exception {
		if(article_id == null || "".equals(article_id) || "null".equals(article_id) ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		List<Map> allcomment = commentDaoImpl.selectAllCommentByArticleid(article_id);
		for (Map map : allcomment) {
			Userinfo userinfo = userinfoDaoImpl.selectUserByUserid((String) map.get("user_id"));
			map.put("username", userinfo.getUser_name());
		}
		return allcomment;
	}

	@Override
	public int getNotifyCommentNum(String tokenid) throws Exception {
		String user_id = userloginDaoImpl.selectUseridByTokenid(tokenid);
		if(user_id == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		int selectAllcommentNotifiNum = commentDaoImpl.selectAllcommentNotifiNum(map);
		return selectAllcommentNotifiNum;
	}
	
	@Override
	public List<Map> getNotifyComment(Map commentMap) throws Exception {
		String user_id = (String) commentMap.get("user_id");
		String pagenum = (String) commentMap.get("pagenum");
		String pagesize = (String) commentMap.get("pagesize");
		
		if(pagenum == null || "".equals(pagenum) || "null".equals(pagenum) || pagesize == null || "".equals(pagesize) || "null".equals(pagesize) ){
			System.out.println("**************");
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		int start = 0;
		int size = 0;
		try{
			start = Integer.valueOf(pagenum) * Integer.valueOf(pagesize);
			size = Integer.valueOf(pagesize);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		commentMap.clear();
		commentMap.put("user_id", user_id);
		commentMap.put("start", start);
		commentMap.put("pagesize", size);
		List<Map> selectAllcommentNotifi = commentDaoImpl.selectAllcommentNotifi(commentMap);
		commentMap.put("comment_read_time", "1");
		notifiinfoDaoImpl.upadteReadTime(commentMap);
		for (Map map : selectAllcommentNotifi) {
			Userinfo userinfo = userinfoDaoImpl.selectUserByUserid((String) map.get("user_id"));
			map.put("username", userinfo.getUser_name());
		}
		return selectAllcommentNotifi;
	}
}
