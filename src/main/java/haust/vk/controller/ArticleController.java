package haust.vk.controller;

import haust.vk.service.ArticleService;
import haust.vk.utils.JsonToMap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value="article")
public class ArticleController {
	
	@Resource
	private ArticleService articleServiceImpl;
	
	@Resource
	private JsonToMap jsonToMap;
	
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public @ResponseBody Map insertArticle(@RequestBody String article){
		Map articleMap = null;
		
		try {
			article = new String(article.getBytes("ISO-8859-1"),"UTF-8");
			articleMap = jsonToMap.jsonToMapUtil(article);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.put("success", -1);
			articleMap.put("messcode", 2);
			e.printStackTrace();
		}
		
		try {
			articleMap = articleServiceImpl.insertArticle(articleMap);
		} catch (Exception e) {
			articleMap.clear();
			articleMap.put("success", "-1");
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
		}
		return articleMap;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public Map deleteArticle(@RequestBody String deleteinfo){
		System.out.println(deleteinfo);
		Map articleMap = null;
		try {
			articleMap = jsonToMap.jsonToMapUtil(deleteinfo);
		} catch (Exception e) {
			articleMap.put("success", -1);
			articleMap.put("messcode", 2);
			e.printStackTrace();
			return articleMap;
		}
		try {
			articleMap = articleServiceImpl.deleteArticle(articleMap);
		} catch (Exception e) {
			e.printStackTrace();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			return articleMap;
		}
		return articleMap;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Map updateArticle(@RequestBody String deleteinfo){
		Map articleMap = new HashMap();
		
		try {
			articleMap = jsonToMap.jsonToMapUtil(deleteinfo);
		} catch (Exception e1) {
			articleMap.put("success", -1);
			articleMap.put("messcode", 2);
			e1.printStackTrace();
			return articleMap;
		}
		
		try {
			articleMap = articleServiceImpl.updateArticle(articleMap);
		} catch (Exception e) {
			e.printStackTrace();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			return articleMap;
		}
		return articleMap;
	}
	
	/**
	 * 获取文章详情
	 * @param articleid
	 * @return
	 */
	@RequestMapping(value="/select/{articleid}")
	public Map selectByArticleid(@PathVariable String articleid){
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleDetail(articleid);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
			return articleMap;
		}
		return articleMap;
	}
	
	/**
	 * 基于时间轴的个人文章列表 分页
	 * @param user_path
	 * @return
	 */
	@RequestMapping(value="/{user_path}")
	public Map selectByUserpath(@PathVariable String user_path){
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(user_path);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
			return articleMap;
		}
		return articleMap;
	}
	
	/**
	 * 基于时间轴的个人关注  文章列表 分页
	 * @param user_path
	 * @return
	 */
	@RequestMapping(value="/{user_path}")
	public Map selectByUserfollow(@PathVariable String user_path){
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(user_path);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
			return articleMap;
		}
		return articleMap;
	}
	
	/**
	 * 通过用户user_path 以及classifyid 获取该分类下的文章列表  分页
	 * @param user_path
	 * @param classifyid
	 * @return
	 */
	@RequestMapping(value="classify/{user_path}/{classifyid}")
	public Map selectByClassify(@PathVariable String user_path,@PathVariable String classifyid){
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(user_path);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
			return articleMap;
		}
		return articleMap;
	}
	
	/**
	 * 通过用户user_path 以及classifyid 获取该分类下的文章列表  分页
	 * @param user_path
	 * @param classifyid
	 * @return
	 */
	@RequestMapping(value="follow/{token_id}")
	public Map selectByTokenid(@PathVariable String token_id){
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(token_id);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
			return articleMap;
		}
		return articleMap;
	}
	
	/**
	 * 基于标题关键字检索文章标题  分页
	 * @param user_path
	 * @param classifyid
	 * @return
	 */
	@RequestMapping(value="follow/{token_id}")
	public Map selectByKeyword(@PathVariable String token_id){
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(token_id);
		} catch (Exception e) {
			articleMap = new HashMap();
			articleMap.clear();
			articleMap.put("success", -1);
			articleMap.put("messcode", "5 不可预见的错误");
			e.printStackTrace();
			return articleMap;
		}
		return articleMap;
	}
}
