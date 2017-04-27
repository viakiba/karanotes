package haust.vk.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.ClassifyDao;
import haust.vk.dao.CollectDao;
import haust.vk.dao.CommentDao;
import haust.vk.dao.FollowDao;
import haust.vk.dao.PraiseDao;
import haust.vk.dao.UserinfoDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Articleabstract;
import haust.vk.entity.Articletag;
import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.service.ArticleService;
import haust.vk.utils.SnowflakeIdUtil;

/**
 * 文章相关 业务代码
 * @author viakiba
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService{
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private UserloginDao userloginDaoImpl;
	@Resource
	private ArticleDao articleDaoImpl;
	@Resource
	private UserinfoDao userinfoDaoImpl;
	@Resource
	private FollowDao followDaoImpl;
	@Resource
	private ClassifyDao classifyDaoImpl;
	@Resource
	private PraiseDao praiseDaoImpl;
	@Resource
	private CollectDao collectDaoImpl;
	@Resource
	private CommentDao commentDaoImpl;
	
	/**
	 * 插入文章
	 */
	@Override
	public Map insertArticle(Map articleMapInfo) throws Exception{
		
		//标签
		String tag = articleMapInfo.get("tag_content").toString();
		String articletag = "{\"tag_content\":"+((String) tag)+"}";
		articletag = "{\"tag_content\":"+articleMapInfo.get("tag_content").toString()+"}";
	
		String abstract_content = (String) articleMapInfo.get("abstract_content");
		String article_content = (String) articleMapInfo.get("article_content");
		String token_id = (String) articleMapInfo.get("token_id");
		String article_title = (String) articleMapInfo.get("article_title");
		String classify_id = (String) articleMapInfo.get("classify_id");
		String user_id = (String) articleMapInfo.get("user_id");
		String article_attachment = (String) articleMapInfo.get("article_attachment");
		
		String article_show_img = (String) articleMapInfo.get("article_show_img");
		if( article_show_img== null || "".equals(article_show_img) || "null".equals(article_show_img)){
			article_show_img = user_id+".png";
		}
		
		//简要
		Articleabstract article_abstract = new Articleabstract();
		article_abstract.setArticle_title(article_title);
		article_abstract.setClassify_id(classify_id);
		article_abstract.setArticle_attachment((String) articleMapInfo.get("article_atta"));
		article_abstract.setArticle_show_img(article_show_img);
		article_abstract.setAbstract_content(abstract_content);
		if("".equals(article_attachment) || "null".equals(article_attachment) || article_attachment == null){
			article_attachment = "";
		}
		article_abstract.setArticle_attachment(article_attachment);
		//生成articleid  绑定userid  初始化简介的参数
		long article_id = snowflakeIdUtil.nextId();
		
		//处理实际存入数据库的数据  `articleabstract` `articlecontent` `articletag`
		//articleabstract  article_create_time使用默认值
		article_abstract.setUser_id(user_id);
		article_abstract.setArticle_id(String.valueOf(article_id));
		//articlecontent 使用Map
		Map articlecontentmap = new HashMap();
		articlecontentmap.put("article_id", String.valueOf(article_id));
		articlecontentmap.put("article_content", String.valueOf(article_content));
		//articletag 
		Articletag article_tag = new Articletag();
		article_tag.setArticle_id(String.valueOf(article_id));
		article_tag.setUser_id(user_id);
		article_tag.setTag_content(articletag);
		//存入数据库
		articleDaoImpl.insertArticletag(article_tag);
		articleDaoImpl.insertArticleabstract(article_abstract);
		articleDaoImpl.insertArticlecontent(articlecontentmap);
		articleMapInfo.clear();
		articleMapInfo.put("messcode", 4);
		articleMapInfo.put("success", 1);
		articleMapInfo.put("article_id", article_id);
		return articleMapInfo;
	}

	/**
	 * 删除文章  缺陷
	 */
	@Override
	public Map deleteArticle(Map articlemap) throws Exception {
		String article_id = (String) articlemap.get("article_id");
		articleDaoImpl.deleteArticleabstract(article_id);
		articleDaoImpl.deleteArticlecontent(article_id);
		articleDaoImpl.deleteArticletag(article_id);
		praiseDaoImpl.deletePraiseByArticleId(article_id);
		collectDaoImpl.deleteCollectByArticleid(article_id);
		commentDaoImpl.deleteCommentByArticleid(article_id);
		return articlemap;
	}

	/**
	 * 更新文章
	 */
	@Override
	public Map updateArticle(Map articleMapInfo) throws Exception {
		String article_id = (String) articleMapInfo.get("article_id");
		String article_title = (String) articleMapInfo.get("article_title");
		String classify_id = (String) articleMapInfo.get("classify_id");
		String article_show_img = (String) articleMapInfo.get("article_show_img");
		String abstract_content = (String) articleMapInfo.get("abstract_content");
		String article_content = (String) articleMapInfo.get("article_content");
		String tag_content = articleMapInfo.get("tag_content").toString();
		String user_id = (String) articleMapInfo.get("user_id");
		
		if( article_id==null || "".equals(article_id) || "null".equals(article_id) || article_title==null || "".equals(article_title) || "null".equals(article_title) || classify_id==null || "".equals(classify_id) || "null".equals(classify_id)|| abstract_content==null || "".equals(abstract_content) || "null".equals(abstract_content) || article_content==null || "".equals(article_content) || "null".equals(article_content) ){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		//标签
		String articletag = "{\"tag_content\":"+tag_content+"}";
		//简要
		Articleabstract article_abstract = new Articleabstract();
		//生成article_update_time
		Timestamp article_update_time = new Timestamp((new Date()).getTime());
		//处理实际存入数据库的数据  `articleabstract` `articlecontent` `articletag`
		article_abstract.setUser_id(user_id);
		article_abstract.setArticle_update_time(article_update_time);
		article_abstract.setArticle_id(article_id);
		article_abstract.setArticle_title(article_title);
		article_abstract.setClassify_id(classify_id);
		article_abstract.setArticle_show_img(article_show_img);
		if( abstract_content == null){
			article_abstract.setAbstract_content("");
		}else{
			article_abstract.setAbstract_content(abstract_content);
		}
		if(abstract_content==null | abstract_content.length() == 0){
			if(article_content.length()>100){
				article_abstract.setAbstract_content(article_content.substring(0, 100));
			}else{
				article_abstract.setAbstract_content(article_content);
			}
		}
		
		//articlecontent 使用Map
		Map articlecontentmap = new HashMap();
		articlecontentmap.put("article_id", article_abstract.getArticle_id());
		articlecontentmap.put("article_content", String.valueOf(article_content));
		//articletag 
		Articletag article_tag = new Articletag();
		article_tag.setArticle_id(article_id);
		article_tag.setUser_id(user_id);
		article_tag.setTag_content(articletag);
		//存入数据库
		articleDaoImpl.updateArticletag(article_tag);
		articleDaoImpl.updateArticleabstract(article_abstract);
		articleDaoImpl.updateArticlecontent(articlecontentmap);
		return null;
	}
	
	/**
	 * 获取文章详情
	 */
	@Override
	public Map selectArticleDetail(Map map) throws Exception {
		Userinfo userinfo = userloginDaoImpl.selectUserloginByTokenid( ( (String) map.get("token_id")) );
		String articleid = (String) map.get("article_id");
		
		String article_content = articleDaoImpl.selectArticleContent(articleid);
		if(article_content == null){
			return new HashMap<>();
		}
		Map abstractdetail = (Map) articleDaoImpl.selectArticleAbstract(articleid);
		String tag_content = (String) articleDaoImpl.selectArticleTag(articleid);
		
		String classify_id = (String) abstractdetail.get("classify_id");
		String classify_content = (String) articleDaoImpl.selectArticleClassify(classify_id);
		abstractdetail.put("article_content", article_content);
		abstractdetail.put("tag_content", tag_content.substring(16,tag_content.length()-1));
		abstractdetail.put("classify_content", classify_content);
		
		if(userinfo != null){
			map.put("user_id", userinfo.getUser_id());
			//判断是否点赞
			Map temp = praiseDaoImpl.selectPraiseByUseridAndArticleid(map);
			if(temp == null){
				abstractdetail.put("is_praise", "0");
			}else{
				abstractdetail.put("is_praise", "1");
			}
			//判断是否收藏
			temp = collectDaoImpl.selectCollectByUseridAndArticleid(map);
			if(temp == null){
				abstractdetail.put("is_collect", "0");
			}else{
				abstractdetail.put("is_collect", "1");
			}
		}else{
			abstractdetail.put("is_praise", "0");
			abstractdetail.put("is_collect", "0");
		}
		return abstractdetail;
	}
	
	/**
	 * 获取文章列表 个人
	 */
	@Override
	public List<Articleabstract> selectArticleListByUserpath(String userpath,Integer beginnum,Integer shownum) throws Exception {
		Map map = new HashMap();
		System.out.println(beginnum+"************"+shownum);
		map.put("user_path", userpath);
		map.put("start", beginnum * shownum);
		map.put("pagesize", shownum);
		List<Articleabstract> list = articleDaoImpl.selectArticleListByPerson(map);
		return list;
	}
	
	/**
	 * 获取跟人分类下的文章列表
	 */
	@Override
	public Map selectArticleByUserClassifyArticleList(String userpath,String classifyid, Integer beginnum, Integer shownum)
			throws Exception {
		Map map = new HashMap();
		System.out.println(beginnum+"************"+shownum);
		map.put("user_path", userpath);
		map.put("classify_id", classifyid);
		map.put("start", beginnum * shownum);
		map.put("pagesize", shownum);
		List<Map> list = articleDaoImpl.selectArticleByClassifyid(map);
		for (Map map2 : list) {
			Map map3 = classifyDaoImpl.selectContentByClassifyid(map2);
			String classify_content = (String) map3.get("classify_content");
			map2.put("classify_content", classify_content);
		}
		Userinfo selectByUserpath = userinfoDaoImpl.selectByUserpath(userpath);
		map.clear();
		selectByUserpath.setUser_password("");
		map.put("userinfo", selectByUserpath);
		map.put("articlelist", list);
		return map;
	}
	
	@Override
	public List<Map> selectFollowArticleListByUserpath(Map map) throws Exception {
		Map res = new HashMap<>();
		Integer pagenum = null;
		Integer pagesize = null;
		String keyword = (String) map.get("keywords");
		try{
			pagenum = Integer.valueOf( (String) map.get("pagenum"));
		    pagesize = Integer.valueOf((String) map.get("pagesize"));
		}catch (NumberFormatException e){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		
		String userid = (String) map.get("user_id");
		List<String> followuserlist = followDaoImpl.selectFollowNotifyByUseridHelp(map);
		followuserlist.add(userid);
			if(followuserlist.size() > 0){
				res.put("start", pagenum*pagesize);
				res.put("pagesize", pagesize);
				res.put("followuserlist", followuserlist);
				if(keyword != null){
					keyword.trim();
					res.put("keywords", keyword);
				}
				List<Map> list = articleDaoImpl.selectArticleByListUserid(res);
				//获取文章列表
				
				//
				for (Map temp : list) {
					System.out.println(temp.toString());
					String user_id = (String) temp.get("user_id");
					Userinfo userinfo = userinfoDaoImpl.selectUserByUserid(user_id);
					temp.put("userinfo", userinfo);
				}
				
				return list;
		}
			List<Map> lists = new ArrayList<>();
			return lists;
	}
	
	@Override
	public List<Map> selectArticleListByKeyword(Map map) throws GlobalErrorInfoException,Exception {
		String pagenum = (String) map.get("pagenum");
		String pagesize = (String) map.get("pagesize");
		String keyword = (String) map.get("keywords");
		if( pagesize == null || "".equals(pagesize) || "null".equals(pagesize)||pagenum == null || "".equals(pagenum) || "null".equals(pagenum)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		Integer start = 0;
		
		try{
			Integer pagenums = Integer.valueOf(pagenum);
			Integer pagesizes = Integer.valueOf(pagesize);
			start = pagenums * pagesizes;
		}catch(NumberFormatException e){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		map.put("start", start);
		map.put("pagesize", Integer.valueOf(pagesize));
		List<Map> list = articleDaoImpl.selectArticleBySearch(map);
		for (Map temp : list) {
			String user_id = (String) temp.get("user_id");
			String classify_id = (String) temp.get("classify_id");
			Map classify = classifyDaoImpl.selectContentByClassifyid(temp);
			temp.put("classify_content", ( (String) classify.get("classify_content")));
			Userinfo selectUserByUserid = userinfoDaoImpl.selectUserByUserid(user_id);
			selectUserByUserid.setUser_password("");
			temp.put("userinfo", selectUserByUserid);
		}
		return list;
	}
}
