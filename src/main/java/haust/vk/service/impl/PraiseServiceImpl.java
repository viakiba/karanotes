package haust.vk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.NotifiinfoDao;
import haust.vk.dao.PraiseDao;
import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.PraiseService;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class PraiseServiceImpl implements PraiseService{
	
	@Resource
	private PraiseDao praiseDaoImpl;
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private ArticleDao articleDaoImpl;
	@Resource
	private UserloginDao userloginDaoImpl; 
	@Resource
	private NotifiinfoDao notifiinfoDaoImpl;
	@Resource
	private UserinfoDao userinfoDaoImpl;
	
	@Override
	public int insertPraise(Map praiseMap) throws Exception {
		String user_id = (String) praiseMap.get("user_id");
		String article_id = (String) praiseMap.get("article_id");
		String praise_user_id = (String) praiseMap.get("author_id");
		
		if( article_id == null || "".equals(article_id) || "null".equals(article_id) || praise_user_id == null || "".equals(praise_user_id) || "null".equals(praise_user_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		praiseMap.put("praise_user_id", praise_user_id);
		//先判断是否已经点过赞
		int is_praise =praiseDaoImpl.selectPraiseByUseridAndPraiseUserid(praiseMap);
		//插入点赞
		if(is_praise == 0){
			praiseMap.put("praise_id", String.valueOf(snowflakeIdUtil.nextId()));
			praiseDaoImpl.insertPraise(praiseMap);
			//得到点赞数量
			Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
			Integer comment_num = (Integer) selectArticleAbstract.get("praise_num");
			//更新点赞数量
			Map map = new HashMap<>();
			map.put("article_id", article_id);
			map.put("praise_num", Integer.valueOf( comment_num ) + 1);
			articleDaoImpl.updateOneNum(map);
			return 1;
		}else{
			return 0;
		}
		
	}
	
	
	@Override
	public void deletePraise(Map praiseMap) throws Exception {
		String user_id = (String) praiseMap.get("user_id");
		String article_id = (String) praiseMap.get("article_id");
		String praise_user_id = (String) praiseMap.get("author_id");
		
		if( article_id == null || "".equals(article_id) || "null".equals(article_id) || praise_user_id == null || "".equals(praise_user_id) || "null".equals(praise_user_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		//删除点赞记录
		praiseMap.put("praise_user_id", praise_user_id);
		praiseDaoImpl.deletePraise(praiseMap);
		//得到点赞数量
		Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
		Integer comment_num = (Integer) selectArticleAbstract.get("praise_num");
		//更新点赞数量
		Map commentMap = new HashMap<>();
		commentMap.put("article_id", article_id);
		commentMap.put("praise_num", Integer.valueOf( comment_num ) - 1);
		articleDaoImpl.updateOneNum(commentMap);
	}
	
	@Override
	public int getPraiseNotifiNum(String tokenid) throws Exception {
		String user_id = userloginDaoImpl.selectUseridByTokenid(tokenid);
		if(user_id == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		int not_read_praise_num = praiseDaoImpl.selectPraiseNotifyNumByUserid(user_id);
		return not_read_praise_num;
	}
	
	@Override
	public List<Map> getPraisenifify(Map praiseMap) throws Exception {
		String user_id = (String) praiseMap.get("user_id");
		String pagenum = (String) praiseMap.get("pagenum");
		String pagesize = (String) praiseMap.get("pagesize");
		
		if(pagenum == null || "".equals(pagenum) || "null".equals(pagenum) || pagesize == null || "".equals(pagesize) || "null".equals(pagesize) ){
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
		praiseMap.clear();
		praiseMap.put("user_id", user_id);
		praiseMap.put("start", start);
		praiseMap.put("pagesize", size);
		List<Map> selectAllcommentNotifi = praiseDaoImpl.selectAllPraise(praiseMap);
		praiseMap.put("praise_read_time", "1");
		notifiinfoDaoImpl.upadteReadTime(praiseMap);
		for (Map map : selectAllcommentNotifi) {
			Userinfo userinfo = userinfoDaoImpl.selectUserByUserid((String) map.get("user_id"));
			map.put("username", userinfo.getUser_name());
		}
		return selectAllcommentNotifi;
	}
}
