package haust.vk.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.CollectDao;
import haust.vk.dao.NotifiinfoDao;
import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.TokenidErrorInfoEnum;
import haust.vk.service.CollectService;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class CollectServiceImpl implements CollectService{
	
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
	@Resource
	private CollectDao collectDaoImpl;
	
	/**
	 * 添加收藏
	 */
	@Override
	public Map insertCollect(Map collectMap) throws Exception {
		String user_id = (String) collectMap.get("user_id");
		String article_id = (String) collectMap.get("article_id");
		String author_id = (String) collectMap.get("author_id");
		if( article_id == null || "".equals(article_id) || "null".equals(article_id) || author_id == null || "".equals(author_id) || "null".equals(author_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		//先判断是否已经收藏
		int i = collectDaoImpl.selectIsCollect(collectMap);
		if(i>0){
			collectMap.clear();
			collectMap.put("operate", "0");
			return collectMap;
		}else{
			//插入收藏
			collectMap.put("collect_id",  String.valueOf(snowflakeIdUtil.nextId()));
			collectDaoImpl.insertCollect(collectMap);
			//得到收藏数量
			Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
			Integer collect_num = (Integer) selectArticleAbstract.get("collect_num");
			//更新收藏数量
			Map map = new HashMap<>();
			map.put("article_id", article_id);
			map.put("collect_num", Integer.valueOf( collect_num ) + 1);
			articleDaoImpl.updateOneNum(map);
			collectMap.clear();
			collectMap.put("operate", "1");
			return collectMap;
		}
		
	}
	
	/**
	 * 取消收藏
	 */
	@Override
	public void deleteCollect(Map praiseMap) throws Exception {
		String user_id = (String) praiseMap.get("user_id");
		String article_id = (String) praiseMap.get("article_id");
		String author_id = (String) praiseMap.get("author_id");
		if( article_id == null || "".equals(article_id) || "null".equals(article_id) || author_id == null || "".equals(author_id) || "null".equals(author_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		//删除收藏记录
		int i = collectDaoImpl.deleteCollect(praiseMap);
		if(i>0){
			//得到收藏数量
			Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
			Integer comment_num = (Integer) selectArticleAbstract.get("collect_num");
			//更新收藏数量
			Map map = new HashMap<>();
			map.put("article_id", article_id);
			map.put("collect_num", Integer.valueOf( comment_num ) - 1);
			articleDaoImpl.updateOneNum(map);
		}
	}
	
	/**
	 * 用户收藏文章 作者通知 新 数量
	 */
	@Override
	public int getPraiseNotifiNum(String tokenid) throws Exception {
		String user_id = userloginDaoImpl.selectUseridByTokenid(tokenid);
		if(user_id == null){
			throw new GlobalErrorInfoException(TokenidErrorInfoEnum.USER_CONNOT_BE_FOUND);
		}
		Map map = new HashMap<>();
		map.put("user_id", user_id);
		int not_read_praise_num = collectDaoImpl.selectCollectNotifyByUserid(user_id);
		return not_read_praise_num;
	}
	
	/**
	 * 收藏列表  分页
	 */
	@Override
	public List<Map> selectCollectList(Map collectMap) throws Exception {
		String pagenum = (String) collectMap.get("pagenum");
		String pagesize = (String) collectMap.get("pagesize");
		String user_id = (String) collectMap.get("user_id");
		
		if( pagenum == null || "".equals(pagenum) || "null".equals(pagenum) || pagesize == null || "".equals(pagesize) || "null".equals(pagesize)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		int start = 0;
		try{
			start = Integer.valueOf(pagenum) * Integer.valueOf(pagesize);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		collectMap.put("start", start);
		collectMap.put("pagesize", Integer.valueOf(pagesize) );
		List<Map> list = collectDaoImpl.selectCollectListByUserid(collectMap);
		List<Map> lists = new LinkedList<>();
		if(list.size() > 0){
			for (Map map : list) {
				String article_id = (String) map.get("article_id");
				String author_id = (String) map.get("author_id");
				Userinfo userinfo = userinfoDaoImpl.selectUserByUserid(author_id);
				userinfo.setUser_password("");
				Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract(article_id);
				selectArticleAbstract.put("userinfo", userinfo);
				//selectArticleAbstract.put("collect_create_time", String.valueOf( map.get("collect_create_time") ));
				lists.add(selectArticleAbstract);
			}
			return lists;
		}else{
			return lists;
		}
	}
	
	/**
	 * 收藏 通知详情
	 */
	@Override
	public List<Map> getCollectNotify(Map collectMap) throws Exception {
		String user_id = (String) collectMap.get("user_id");
		String pagenum = (String) collectMap.get("pagenum");
		String pagesize = (String) collectMap.get("pagesize");
		
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
		
		collectMap.clear();
		collectMap.put("user_id", user_id);
		collectMap.put("start", start);
		collectMap.put("pagesize", size);
		List<Map> selectAllcommentNotifi = collectDaoImpl.selectCollectNotifi(collectMap);
		collectMap.put("collect_read_time", "1");
		notifiinfoDaoImpl.upadteReadTime(collectMap);
		
		for (Map map : selectAllcommentNotifi) {
			Userinfo userinfo = userinfoDaoImpl.selectUserByUserid((String) map.get("user_id"));
			Map selectArticleAbstract = articleDaoImpl.selectArticleAbstract((String) map.get("article_id"));
			System.out.println(map.get("article_id")+"******");
			map.put("username", userinfo.getUser_name());
			map.put("article_title", selectArticleAbstract.get("article_title"));
		}
		
		return selectAllcommentNotifi;
	}
}
