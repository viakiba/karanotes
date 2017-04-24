package haust.vk.service;

import java.util.List;
import java.util.Map;

import haust.vk.entity.Articleabstract;

public interface ArticleService {
	
	/**
	 * 增加文章
	 * @param articleMap
	 * @param device_type
	 * @return
	 */
	public Map insertArticle(Map articleMapInfo) throws Exception;
	/**
	 * 删除文章
	 * @param articleMap
	 * @return
	 */
	public Map deleteArticle(Map articleMapInfo) throws Exception;
	/**
	 * 更新文章
	 * @param deleteinfo
	 * @param string
	 * @return
	 */
	public Map updateArticle(Map articleMapInfo) throws Exception;
	/**
	 * 拿到文章的详情内容
	 * @param articleid
	 * @return
	 */
	public Map selectArticleDetail(String articleid) throws Exception;
	/**
	 * 拿到个人文章列表  分页  不分类别
	 * @param user_path
	 * @return
	 * @throws Exception
	 */
	public List<Articleabstract> selectArticleListByUserpath(String user_path,Integer beginnum,Integer shownum) throws Exception;
	/**
	 * 分页  获取关注人的列表 时间轴
	 * @param userpath
	 * @param beginnum
	 * @param shownum
	 * @return
	 * @throws Exception
	 */
	public Map selectFollowArticleListByUserpath(String userpath,Integer beginnum, Integer shownum) throws Exception;
	/**
	 * 拿到个人文章列表  分页  分类别
	 * @param userpath
	 * @param classifyid
	 * @param beginnum
	 * @param shownum
	 * @return
	 * @throws Exception
	 */
	public Map selectArticleByUserClassifyArticleList(String userpath,String classifyid, Integer beginnum, Integer shownum) throws Exception;
	/**
	 * 通过关键字模糊搜索标题  分页
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public Map selectArticleListByKeyword(String keyword,Integer beginnum, Integer shownum) throws Exception;
	
}
