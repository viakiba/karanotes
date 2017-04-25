package haust.vk.api;

import haust.vk.entity.Articleabstract;
import haust.vk.entity.Userinfo;
import haust.vk.exception.GlobalErrorInfoException;
import haust.vk.exception.code.JsonKeyValueErrorInfoEnum;
import haust.vk.exception.code.NodescribeErrorInfoEnum;
import haust.vk.exception.code.SuccessMessageCodeInfoEnum;
import haust.vk.result.ResultBody;
import haust.vk.service.ArticleService;
import haust.vk.utils.JsonToMap;

import java.util.HashMap;
import java.util.List;
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
	public ResultBody updateArticle(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map articleMapInfo = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		articleMapInfo.put("user_id", userinfo.getUser_id());
		try {
			articleServiceImpl.updateArticle(articleMapInfo);
		} catch (Exception e) {
			logger.debug(e);
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		return new ResultBody(new HashMap());
	}
	
	@RequestMapping(value="/extra/article/{articleid}")
	public ResultBody selectByArticleid(@PathVariable String articleid) throws GlobalErrorInfoException{
		Map articleMap = null;
		if(articleid == null || "".equals(articleid) || "null".equals(articleid)){
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		}
		try {
			articleMap = articleServiceImpl.selectArticleDetail(articleid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(articleMap);
	}

	@RequestMapping(value="/extra/articlelist/alllist/{userpath}/{beginnum}/{shownum}")
	public ResultBody selectByUserpath(@PathVariable String userpath,@PathVariable Integer beginnum,@PathVariable Integer shownum) throws GlobalErrorInfoException{
		List<Articleabstract> articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByUserpath(userpath,beginnum,shownum);
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(articleMap);
	}
	
	@RequestMapping(value="/article/followlist/{userpath}")
	public ResultBody selectByUserfollow(HttpServletRequest req,HttpServletResponse resp) throws GlobalErrorInfoException{
		Map jsoninfo = (Map) req.getAttribute("jsoninfo");
		Userinfo userinfo = (Userinfo) req.getAttribute("userinfo");
		jsoninfo.put("user_id", userinfo.getUser_id());
		List<Map> list = null;
		try {
			list = articleServiceImpl.selectFollowArticleListByUserpath(jsoninfo);
		}catch (GlobalErrorInfoException e) {
			throw new GlobalErrorInfoException(JsonKeyValueErrorInfoEnum.JSON_KEYVALUE_ERROR);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
	
	@RequestMapping(value="/extra/articlelist/classify/{userpath}/{classifyid}/{beginnum}/{shownum}")
	public ResultBody selectByClassify(@PathVariable String userpath,@PathVariable String classifyid,@PathVariable Integer beginnum,@PathVariable Integer shownum) throws GlobalErrorInfoException{
		Map  list = null;
		try {
			list= articleServiceImpl.selectArticleByUserClassifyArticleList(userpath,classifyid,beginnum,shownum);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(list);
	}
	
	@RequestMapping(value="/extra/article/title/{keyword}/{beginnum}/{shownum}")
	public ResultBody selectArticleListByKeyword(@PathVariable String keyword,@PathVariable Integer beginnum,@PathVariable Integer shownum,String userpath) throws GlobalErrorInfoException{
		List<Map>  articleMap = null;
		try {
			articleMap = articleServiceImpl.selectArticleListByKeyword(keyword,beginnum,shownum,userpath);
		} catch (Exception e) {
			throw new GlobalErrorInfoException(NodescribeErrorInfoEnum.NO_DESCRIBE_ERROR);
		}
		return new ResultBody(articleMap);
	}
}
