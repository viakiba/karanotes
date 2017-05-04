package haust.vk.service;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Articleabstract;

public interface ArticleService {
	
	public Map insertArticle(Map articleMapInfo) throws Exception;

	public Map deleteArticle(Map articleMapInfo) throws Exception;

	public Map updateArticle(Map articleMapInfo) throws Exception;

	public Map selectArticleDetail(Map map) throws Exception;

	public List<Articleabstract> selectArticleListByUserpath(String user_path,Integer beginnum,Integer shownum) throws Exception;

	public List<Map> selectFollowArticleListByUserpath(Map map) throws Exception;

	public Map selectArticleByUserClassifyArticleList(String userpath,String classifyid, Integer beginnum, Integer shownum) throws Exception;
	
	public List<Map>  selectArticleListByKeyword(Map map) throws Exception;

	public List<Map> selectIndexArticleList(Map jsoninfo) throws Exception;
	
}
