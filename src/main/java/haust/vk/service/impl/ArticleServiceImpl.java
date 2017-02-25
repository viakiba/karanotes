package haust.vk.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
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
	public Map insertArticle(String articleMap,String device_type) {
		//处理接收的数据   文章标题    分类   标签   正文     用户token_id
		Map parseObject = JSON.parseObject(articleMap, Map.class );
		
		//标签
		Object tag_content_object = parseObject.get("tag_content");
		String articletag = "{\"tag_content\":"+tag_content_object+"}";
		//正文
		Object article_content_object = parseObject.get("article_content");
		String article_content = article_content_object.toString();
		
		//token_id
		Object token_id_object = parseObject.get("token_id");
		
		//简要
		Object article_abstract_object =  parseObject.get("article_abstract");
		Articleabstract article_abstract = JSON.parseObject(article_abstract_object.toString(), Articleabstract.class);
		
		//生成articleid  绑定userid  初始化简介的参数
		long article_id = snowflakeIdUtil.nextId();
		
		//生成article_create_time
		Timestamp article_create_time = new Timestamp((new Date()).getTime());
		
		//根据tokenid 查找user_id
		String user_id = userloginDaoImpl.selectUseridByTokenid(token_id_object.toString());
		
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
		Map mesmap = new HashMap();
		try{
			articleDaoImpl.insertArticletag(article_tag);
			articleDaoImpl.insertArticleabstract(article_abstract);
			articleDaoImpl.insertArticlecontent(articlecontentmap);
		}catch(Exception e){
			//反馈信息
			mesmap.put("error", "添加失败");
			return mesmap;
		}
		mesmap.put("article_id", article_id);
		return mesmap;
	}

	@Override
	public Map updateArticle(String articleMap) {

		return null;
	}

	@Override
	public Map deleteArticle(String articleMap) {
		Map articlemap = JSON.parseObject(articleMap, Map.class);
		
		Object token_id = articlemap.get("token_id");
		Object article_id = articlemap.get("article_id");
		
		String userid = userloginDaoImpl.selectUseridByTokenid(token_id.toString());
		
		articlemap = new HashMap();
		if(userid != null){
			try{
				articleDaoImpl.deleteArticleabstract(article_id.toString());
				articleDaoImpl.deleteArticlecontent(article_id.toString());
				articleDaoImpl.deleteArticletag(article_id.toString());
			}catch(Exception e){
				articlemap.put("error", "true");
				return articlemap;
			}
		}
		articlemap.put("error", "flase");
		return articlemap;
	}

	@Override
	public Map selectArticleTitle(String articleMap) {
		
		return null;
	}

}
