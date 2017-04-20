package haust.vk.api;

import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.ArticleService;
import haust.vk.utils.JsonToMap;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge=800,origins="*",methods={RequestMethod.GET, RequestMethod.POST})
public class ArticleController {
	private static Logger logger = Logger.getLogger(ArticleController.class);
	@Resource
	private ArticleService articleServiceImpl;
	
	@RequestMapping(value="/article/insert",method=RequestMethod.POST)
	public ResultBody insertArticle(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map articleMapInfo = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		
		String article_content = (String) articleMapInfo.get("article_content");
		String token_id = (String) articleMapInfo.get("token_id");
		String article_title = (String) articleMapInfo.get("article_title");
		String classify_id = (String) articleMapInfo.get("classify_id");
		if( article_content==null || "".equals(article_content) || "null".equals(article_content) || article_title==null || "".equals(article_title) || "null".equals(article_title) || classify_id==null || "".equals(classify_id) || "null".equals(classify_id) ){
			logger.info(articleMapInfo);
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			articleMapInfo.put("user_id", userinfo.getUser_id());
			articleMapInfo = articleServiceImpl.insertArticle(articleMapInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(new HashMap());
	}
	
	@RequestMapping(value="/article/delete",method=RequestMethod.POST)
	public ResultBody deleteArticle(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map articlemap = (Map) req.getAttribute("jsoninfo");
		String article_id = (String) articlemap.get("article_id");
		if( article_id==null || "".equals("article_id") || "".equals(article_id)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			articlemap = articleServiceImpl.deleteArticle(articlemap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(new HashMap());
	}
	
	@RequestMapping(value="/article/update",method=RequestMethod.POST)
	public Map updateArticle(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map articleMap = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		articleMap.put("user_id", userinfo.getUser_id());
		try {
			articleServiceImpl.updateArticle(articleMap);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		throw new GlobalErrorInfoException(SuccessMessageCodeInfoEnum.SUCCESS_CODE_MESSAGE);
	}
	
	@RequestMapping(value="/select/article/{articleid}")
	public @ResponseBody Map selectByArticleid(@PathVariable String articleid) throws GlobalErrorInfoException{
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleDetail(articleid);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return articleMap;
	}
	//zheli
	@RequestMapping(value="/select/article/{userpath}/{beginnum}/{shownum}")
	public Map selectByUserpath(@PathVariable String userpath,@PathVariable Integer beginnum,@PathVariable Integer shownum) throws GlobalErrorInfoException{
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(userpath,beginnum,shownum);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return articleMap;
	}
	
	/**
	 * 基于时间轴的个人关注  文章列表 分页
	 */
	@RequestMapping(value="/select/article/followarticle/{userpath}/{beginnum}/{shownum}")
	public Map selectByUserfollow(@PathVariable String userpath,@PathVariable Integer beginnum,@PathVariable Integer shownum) throws GlobalErrorInfoException{
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectFollowArticleListByUserpath(userpath,beginnum,shownum);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return articleMap;
	}
	
	/**
	 * 通过用户user_path 以及classifyid 获取该分类下的文章列表  分页
	 */
	@RequestMapping(value="/select/classify/{userpath}/{classifyid}/{beginnum}/{shownum}")
	public @ResponseBody Map selectByClassify(@PathVariable String userpath,@PathVariable String classifyid,@PathVariable Integer beginnum,@PathVariable Integer shownum) throws GlobalErrorInfoException{
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleByUserClassifyArticleList(userpath,classifyid,beginnum,shownum);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return articleMap;
	}
	
	/**
	 * 基于标题关键字检索文章标题  分页
	 */
	@RequestMapping(value="/select/article/title/{keyword}/{beginnum}/{shownum}")
	public Map selectArticleListByKeyword(@PathVariable String keyword,@PathVariable Integer beginnum,@PathVariable Integer shownum) throws GlobalErrorInfoException{
		Map articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByKeyword(keyword,beginnum,shownum);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return articleMap;
	}
}
