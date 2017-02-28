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
	public Map insertArticle(Map articleMapInfo) {
		
		//标签
		String articletag = "{\"tag_content\":"+articleMapInfo.get("tag_content").toString()+"}";
		//正文
		String article_content = articleMapInfo.get("article_content").toString();
		
		//token_id
		String token_id = articleMapInfo.get("token_id").toString();
		
		//获取简要的数据
		String article_title = articleMapInfo.get("article_title").toString();
		String classify_id = articleMapInfo.get("classify_id").toString();
		String article_show_img = articleMapInfo.get("article_show_img").toString();
		String abstract_content = articleMapInfo.get("abstract_content").toString();
		
		if( article_content==null | token_id==null | article_title==null | classify_id==null ){
			articleMapInfo.clear();
			articleMapInfo.put("success", -1);
			articleMapInfo.put("messcode", 3);
			return articleMapInfo;
		}
		
		//根据tokenid 查找user_id
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id);
		if( user_id==null ){
			articleMapInfo.clear();
			articleMapInfo.put("success", -1);
			articleMapInfo.put("messcode", 1);
			return articleMapInfo;
		}
		
		//简要
		Articleabstract article_abstract = new Articleabstract();
		article_abstract.setArticle_title(article_title);
		article_abstract.setClassify_id(classify_id);
		if(abstract_content == null | abstract_content.length() == 0){
			if(article_content.length()>100){
				article_abstract.setAbstract_content(article_content.substring(0,100) );
			}else{
				article_abstract.setAbstract_content(article_content);
			}
		}
		article_abstract.setArticle_attachment(articleMapInfo.get("article_atta").toString());
		article_abstract.setArticle_show_img(article_show_img);
		
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
		article_tag.setArticle_id(String.valueOf(article_content));
		article_tag.setUser_id(user_id);
		article_tag.setTag_content(articletag);
		//存入数据库
		try{
			articleDaoImpl.insertArticletag(article_tag);
			articleDaoImpl.insertArticleabstract(article_abstract);
			articleDaoImpl.insertArticlecontent(articlecontentmap);
		}catch(Exception e){
			//反馈信息
			e.printStackTrace();
			articleMapInfo.clear();
			articleMapInfo.put("success", "-1");
			articleMapInfo.put("messcode", "5 不可预见错误");
			return articleMapInfo;
		}
		articleMapInfo.clear();
		articleMapInfo.put("messcode", 4);
		articleMapInfo.put("success", 1);
		articleMapInfo.put("article_id", article_id);
		return articleMapInfo;
	}

	@Override
	public Map deleteArticle(Map articlemap) {

		String token_id = articlemap.get("token_id").toString();
		String article_id = articlemap.get("article_id").toString();
		
		if(token_id==null | article_id==null){
			articlemap.clear();
			articlemap.put("success", -1);
			articlemap.put("messcode", 3);
			return articlemap;
		}
		
		String userid = userloginDaoImpl.selectUseridByTokenid(token_id);
		
		if(userid == null){
			articlemap.clear();
			articlemap.put("success", -1);
			articlemap.put("messcode", 1);
			return articlemap;
		}
		
		if(userid != null){
			try{
				articleDaoImpl.deleteArticleabstract(article_id);
				articleDaoImpl.deleteArticlecontent(article_id);
				articleDaoImpl.deleteArticletag(article_id);
			}catch(Exception e){
				articlemap.clear();
				articlemap.put("success", -1);
				articlemap.put("messcode", "5 不可预见的错误");
				return articlemap;
			}
		}
		articlemap.clear();
		articlemap.put("success", 1);
		articlemap.put("messcode", 4);
		return articlemap;
	}

	@Override
	public Map updateArticle(Map articleMapInfo) {
		//处理接收的数据   文章标题    分类   标签   正文     用户token_id  article_id
		//token_id
		String token_id = articleMapInfo.get("token_id").toString();
		//article_id
		String article_id = articleMapInfo.get("article_id").toString();
		//article_title
		String article_title = articleMapInfo.get("article_title").toString();
		//classify_id
		String classify_id = articleMapInfo.get("classify_id").toString();
		//article_show_img
		String article_show_img = articleMapInfo.get("article_show_img").toString();
		//abstract_content
		String abstract_content = articleMapInfo.get("abstract_content").toString();
		//article_content
		String article_content = articleMapInfo.get("article_content").toString();
		//article_content
		String tag_content = articleMapInfo.get("tag_content").toString();
		
		if(token_id==null | article_id==null | article_title==null | classify_id==null | abstract_content==null | article_content==null ){
			articleMapInfo.clear();
			articleMapInfo.put("success", -1);
			articleMapInfo.put("messcode", 3);
			return articleMapInfo;
		}
		
		//根据tokenid 查找user_id
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id.toString());
		if(user_id == null){
			articleMapInfo.clear();
			articleMapInfo.put("success", -1);
			articleMapInfo.put("messcode", 1);
			return articleMapInfo;
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
		try{
			articleDaoImpl.updateArticletag(article_tag);
			articleDaoImpl.updateArticleabstract(article_abstract);
			articleDaoImpl.updateArticlecontent(articlecontentmap);
		}catch(Exception e){
			//反馈信息
			articlecontentmap.clear();
			articlecontentmap.put("success", -1);
			articlecontentmap.put("messcode", "5 不可预见的错误");
			return articlecontentmap;
		}
		articlecontentmap.clear();
		articlecontentmap.put("success", 1);
		articlecontentmap.put("messcode", 4);
		articlecontentmap.put("article_id", article_id);
		return articlecontentmap;
	}

	@Override
	public List<Map> selectArticleTitle(String articletitleMap) {
		List<Map> articlemap = null;
		try {
			articlemap = articleDaoImpl.selectArticleLikeTitle(articletitleMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articlemap;
	}
}
