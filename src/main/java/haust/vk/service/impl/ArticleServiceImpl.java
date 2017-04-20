package haust.vk.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import haust.vk.dao.ArticleDao;
import haust.vk.dao.UserloginDao;
import haust.vk.entity.Articleabstract;
import haust.vk.entity.Articlecontent;
import haust.vk.entity.Articletag;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.service.ArticleService;
import haust.vk.utils.SnowflakeIdUtil;

@Service
public class ArticleServiceImpl implements ArticleService{
	
	@Resource
	private SnowflakeIdUtil snowflakeIdUtil;
	@Resource
	private UserloginDao userloginDaoImpl;
	@Resource
	private ArticleDao articleDaoImpl;
	
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

	@Override
	public Map deleteArticle(Map articlemap) throws Exception {
		String article_id = (String) articlemap.get("article_id");
		articleDaoImpl.deleteArticleabstract(article_id);
		articleDaoImpl.deleteArticlecontent(article_id);
		articleDaoImpl.deleteArticletag(article_id);
		return articlemap;
	}

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
		article_abstract.setAbstract_content(abstract_content);
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

	@Override
	public Map selectArticleDetail(String articleid) throws Exception {
		Map detail = articleDaoImpl.selectArticleDetail(articleid);
		return detail;
	}

	@Override
	public Map selectArticleListByUserpath(String userpath,Integer beginnum,Integer shownum) throws Exception {
		//未完成  总数
		Map map = new HashMap();
		map.put("user_path", userpath);
		map.put("beginnum", beginnum);
		map.put("shownum", shownum);
		List<Articleabstract> list = articleDaoImpl.selectArticleListByPerson(map);
		return null;
	}
	
	@Override
	public Map selectArticleByUserClassifyArticleList(String userpath,String classifyid, Integer beginnum, Integer shownum)
			throws Exception {
		//未完成
		
		return null;
	}
	
	@Override
	public Map selectFollowArticleListByUserpath(String userpath,Integer beginnum, Integer shownum) throws Exception {
		//未完成
		
		return null;
	}
	
	@Override
	public Map selectArticleListByKeyword(String keyword,Integer beginnum, Integer shownum) throws Exception {
		//未完成
		
		return null;
	}
}
